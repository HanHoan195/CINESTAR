package view;

import repository.FileService;

import javax.swing.plaf.PanelUI;
import java.util.Scanner;

public class AdminView {
    private static final String FILE_ORDER = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\order.csv";
    FileService fileService = new FileService();
    FilmView filmView = new FilmView();
    Scanner scanner = new Scanner(System.in);
    public  void menuRevenue(){
        System.out.println("                               ╔═══════════════════════════════════════════════════════╗");
        System.out.println("                               ║               DOANH THU CINESTAR                      ║");
        System.out.println("                               ║          [1] Xem doanh thu theo ngày                  ║");
        System.out.println("                               ║          [2] Xem doanh thu theo tháng                 ║");
        System.out.println("                               ║          [3] Xem doanh thu theo năm                   ║");
        System.out.println("                               ║          [4] Xem tổng doanh thu                       ║");
        System.out.println("                               ║          [5] Quay lại                                 ║");
        System.out.println("                               ╚═══════════════════════════════════════════════════════╝");
    }

    public void launcherRevenue() {
        OrderView orderView1 = new OrderView();
        int select = 0;
        boolean checkAction = false;
        do {
            menuRevenue();
            System.out.println("Chọn chức năng: ");
            System.out.print("\t➥ ");
            try {
                select = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn phải là một số!");
                select = 0;
                continue;
            }
            switch (select) {
                case 1:
                    orderView1.showRevenueByDay();
                    checkAction = checkActionContinue();
                    break;
                case 2:
                    orderView1.showRevenueByMonth();
                    checkAction = checkActionContinue();
                    break;
                case 3:
                    orderView1.showRevenueByYear();
                    checkAction = checkActionContinue();
                    break;
                case 4 :
                    FilmView filmView1 = new FilmView();
                    filmView1.getRevenueOfFilm();
                    checkAction = checkActionContinue();
                    break;
                case 5:
                    Menu menu = new Menu();
                    menu.manager();
                    break;

                default:
                    System.out.println("Không đúng. Vui lòng nhập lại!");
            }

        } while (!checkAction);
    }



    public boolean checkActionContinue() {
        boolean checkActionContinue = false;
        do {
            System.out.println("Nhập \"Y\" để quay về giao diện trước đó, nhập \"N\" để quay về giao diện Admin!");
            System.out.print("\t➥ ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return false;
                case "N":
                    Menu menu = new Menu();
                    menu.manager();
                default:
                    checkActionContinue = false;
            }
        } while (!checkActionContinue);
        return true;
    }





}




