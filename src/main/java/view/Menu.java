package view;

import model.User;
import service.FilmService;

import java.util.InputMismatchException;
import java.util.Scanner;

import static model.ShowTime.showTime;

public class Menu {
    static FilmView filmView;
    static ShowTimeView showTimeView = new ShowTimeView();
    CustomerView customerView = new CustomerView();


    static FilmService filmService;
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
        System.out.println("\t\t\t\t\t\tCINESTAR XIN CÁM ƠN!");
        System.out.println("\t\t\t\t\t\t  ✌ Hẹn gặp lại ✌");

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
        System.out.println("             ║           [1] Hiển thị danh sách suất chiếu             ║");
        System.out.println("             ║           [2] Thêm suất chiếu                           ║");
        System.out.println("             ║           [3] Xóa suất chiếu                            ║");
        System.out.println("             ║           [4] Cập nhật suất chiếu                       ║");
        System.out.println("             ║           [5] Xem doanh thu                             ║");
        System.out.println("             ║           [6] Quay lại                                  ║");
        System.out.println("             ║           [0] Thoát                                     ║");
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
                        showTimeView.displayAllShowTimes();
                        showTimeView.checkActionContinue();
                        menuManager();
                        break;

                    case 2:
                        //thêm suất chiếu(ok nhưng không thêm đc suất chiếu mới từ suất chiếu mới)
                        showTimeView.addNewShowTime();
                        break;
                    case 3:
                        //xóa xuất chiếu(ok)
                        ShowTimeView showTimeView1 = new ShowTimeView();
                        showTimeView1.deleteShowTime();
                        break;

                    case 4:
                        //cập nhật phim(ok)
                        ShowTimeView showTimeView = new ShowTimeView();
                        showTimeView.editShowTime(showTime);
                        showTimeView.checkActionContinue();
                        menuManager();

                        break;

                    case 5:
                        //xem doanh thu(chưa tét)
                        filmView.getRevenueOfFilm();
                        break;
                    case 6:
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
            }
        }

    }

    public void customer() {
        OrderView orderView = new OrderView();
        menuCustomer();
        System.out.println("Chọn chức năng:");
        System.out.print("\t➥ ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                showTimeView.displayAllShowTimes();
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
                }
            case 3:
                exit();
                break;
        }

    }


    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.manager();
    }
}
