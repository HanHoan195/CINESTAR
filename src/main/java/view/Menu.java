package view;

import model.User;
import service.FilmService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static model.ShowTime.showTime;

public class Menu {

    static ShowTimeView showTimeView = new ShowTimeView();
    CustomerView customerView = new CustomerView();


    FilmService filmService = new FilmService();
    private static User customer = null;
    private static boolean isFinished = false;

    Scanner scanner = new Scanner(System.in);

    public void action() {
        do {
            menuMain();
            System.out.println("Chọn chức năng: ");
            System.out.print("\t➥ ");
            int choice = -1;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (InputMismatchException io) {
                System.out.println("Không đúng! Vui lòng nhập lại!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            switch (choice) {
                case 1:
                    manager();
                    break;
                case 2:
                    customer();
                    break;
                case 3:
                    exit();
                    break;
                default:
                    System.out.println("Không đúng! Vui lòng nhập lại!");
            }
        } while (true);
    }

    public static void menuMain() {
        System.out.println();
        System.out.println("\t\t\t╔══WELCOME TO CINESTAR══╗");
        System.out.println("\t\t\t║    [1] ADMIN          ║ ");
        System.out.println("\t\t\t║    [2] CUSTOMER       ║ ");
        System.out.println("\t\t\t║    [3] EXIT           ║ ");
        System.out.println("\t\t\t╚═══════════════════════╝");
        System.out.println();
    }

    public static void menuCustomer() {
        System.out.println();
        System.out.println("\t\t\t╔══════WELCOME TO CINESTAR══════════════════════╗");
        System.out.println("\t\t\t║    [1] Hiển thị danh sách suất chiếu          ║ ");
        System.out.println("\t\t\t║    [2] Đặt vé xem phim                        ║ ");
        System.out.println("\t\t\t║    [3] Thoát                                  ║ ");
        System.out.println("\t\t\t╚═══════════════════════════════════════════════╝");
        System.out.println();
    }

    public static void exit() {
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tCINESTAR XIN CÁM ƠN!");
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t  ✌ Hẹn gặp lại ✌");

        System.exit(0);
    }

    public static boolean checkLogin() {
        if (customer == null) {
            System.out.println("Vui lòng đăng nhập để sử dụng chức năng này!");
            return false;
        }
        return true;
    }

    public static User login() {
        LoginView loginView = new LoginView();
        boolean isLoggedIn = false;
        User user = null;
        while (!isLoggedIn) {
            try {
                user = loginView.login();
                isLoggedIn = true;
                customer = user;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Có lỗi khi đăng nhập! Vui lòng thử lại!");
                return null;
            }
        }
        return user;
    }

    public static void admin() {
        boolean isLoggedIn = checkLogin();
        if (!isLoggedIn) {
            login();
        }
    }

    public static void menuManager() {
        System.out.println("             ╔═════════════ADMIN CINESTAR══════════════════════════════╗");
        System.out.println("             ║           [1] Quản lý phim                              ║");
        System.out.println("             ║           [2] Quản lý suất chiếu                        ║");
        System.out.println("             ║           [3] Xem doanh thu                             ║");
        System.out.println("             ║           [4] Quay lại                                  ║");
        System.out.println("             ║           [0] Thoát                                     ║");
        System.out.println("             ╚═════════════════════════════════════════════════════════╝");
        System.out.println();

    }

    public static void menuManageFilm() {
        System.out.println("             ╔═════════════Film Management═════════════════════════════╗");
        System.out.println("             ║           [1] Hiển thi danh sách phim                   ║");
        System.out.println("             ║           [2] Thêm phim mới                             ║");
        System.out.println("             ║           [3] Xóa phim                                  ║");
        System.out.println("             ║           [4] Quay lại                                  ║");
        System.out.println("             ╚═════════════════════════════════════════════════════════╝");
        System.out.println();

    }
    public static void manager() {
        boolean isLoggedIn = checkLogin();
        if (!isLoggedIn) {
            login();
        }
        boolean isFinish = false;
        Scanner scanner1 = new Scanner(System.in);
        menuManager();
        FilmView filmView = new FilmView();
        while (!isFinish) {
            try {
                scanner1.nextLine();
                System.out.println("Chọn chức năng: ");
                System.out.print("\t➥ ");
                int choice = scanner1.nextInt();
                switch (choice) {
                    case 1:
                        //hiện thị danh sách phim (ok)
                        manageFilm();
                        //menuManager();
                        break;

                    case 2:
                        manageShowtime();
                        //showTimeView.checkActionContinue();
                        break;
                    case 3:
                        //xem doanh thu(ok)
                        AdminView adminView = new AdminView();
                        adminView.launcherRevenue();
                        break;
                    case 4:
                        //quay lại(ok)
                        menuMain();
                        break;
                    case 0:
                        //thoát(ok)
                        exit();
                        break;
                    default:
                        System.out.println("Chọn chức năng không đúng vui lòng nhập lại");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Chọn chức năng phải là một số!");
                System.out.println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void manageFilm() throws IOException {
        FilmView filmView = new FilmView();
        int choice = 0;

        do {
            menuManageFilm();
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Chọn chức năng: ");
            System.out.print("\t➥ ");
            try {
                choice = Integer.parseInt(scanner1.nextLine());

                    switch (choice) {
                        case 1:
                            filmView.showAllFilms();
                            showTimeView.checkActionContinue();
                            break;
                        case 2:
                            filmView.addNewFilm();//ok
                            showTimeView.checkActionContinue();
                            break;
                        case 3:
                            filmView.deleteFilm();//chưa xóa đc
                            //showTimeView.checkActionContinue();
                            break;
                        case 4:
                            manager();
                            break;
                        default:
                            System.out.println("Không đúng.Vui lòng nhập lại!");
                    }

            } catch (Exception e) {
                System.out.println("Không đúng vui lòng nhập lại! ");
            }


        } while (true);


    }

    public static void manageShowtime() {

        Scanner scanner1 = new Scanner(System.in);
        do {
            menuShowtime();
            System.out.println("Chọn chức năng:");
            System.out.print("\t➥ ");
            try {
                int choice = Integer.parseInt(scanner1.nextLine());
                if (choice > 0) {
                    switch (choice) {
                        case 1:
                            showTimeView.displayAllShowTimes();
                            showTimeView.checkActionContinue();
                            break;
                        case 2:
                            //thêm suất chiếu(không ghi số ghế vào file )
                            showTimeView.addNewShowTime();
                            break;
                        case 3:
                            //xóa xuất chiếu(ok)
                            ShowTimeView showTimeView1 = new ShowTimeView();
                            showTimeView1.deleteShowTime();
                            break;

                        case 4:
                            manager();
                            break;
                        default:
                            System.out.println("Không đúng.Vui lòng nhập lại!");
                    }
                } else {
                    System.out.println("Lựa chọn phải lớn hơn \"0\"");
                }
            } catch (NumberFormatException e) {
                System.out.println("Không đúng.Vui lòng nhập lại!");
            }

        } while (true);


    }

    public static void menuShowtime() {
        System.out.println();
        System.out.println("\t\t\t╔══════Showtime Management══════════════════════╗");
        System.out.println("\t\t\t║    [1] Hiển thị danh sách suất chiếu          ║ ");
        System.out.println("\t\t\t║    [2] Thêm suất chiếu                        ║ ");
        System.out.println("\t\t\t║    [3] Xóa suất chiếu                         ║ ");
        System.out.println("\t\t\t║    [4] Quay về                                ║ ");
        System.out.println("\t\t\t╚═══════════════════════════════════════════════╝");
        System.out.println();
    }

    public void customer() {
        boolean check = false;
        int choice = 0;
        OrderView orderView = new OrderView();
        menuCustomer();
        System.out.println();
        while (!check) {
            try {
                System.out.println("Chọn chức năng:");
                System.out.print("\t➥ ");
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Không đúng vui lòng nhập lại!");
            }


            switch (choice) {
                case 1:
                    showTimeView.displayAllShowTimes();
                    showTimeView.checkActionContinue();
                    menuCustomer();
                    break;
                case 2:
                    System.out.println("Bạn đã có tài khoản: Y/N");
                    System.out.print("\t➥ ");
                    String choice1 = scanner.nextLine().toUpperCase();
                    switch (choice1) {
                        case "Y":
                            orderView.addNewOrder();
                            break;
                        case "N":
                            System.out.println("Vui lòng đăng ký tài khoản: ");
                            customerView.addNewCustomer();
                            break;

                        default:
                            System.out.println("Không đúng.Vui lòng nhập lại!");
                            break;
                    }
                case 3:
                    exit();
                    break;
            }
        }


    }


    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.manager();
    }
}
