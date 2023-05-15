package view;

import model.EStatus;
import model.EType;
import model.Film;
import service.FilmService;
import service.OrderService;
import utils.CurrencyFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FilmView {
    private FilmView filmView;
    private FilmService filmService;
    private OrderService orderService;
    private ShowTimeView showTimeView;
    Scanner scanner = new Scanner(System.in);
    private Menu menu;


    public FilmView() {
        filmService = new FilmService();
        orderService = new OrderService();
//        filmView = new FilmView();


    }

    public void showAllFilms() {//danh sách phim không có giờ chiếu
        List<Film> listFilm = filmService.getAllFilms();
        System.out.println("                        ╔═══════╦══════════════════════════════╦════════════════╦═══════════════╗");
        System.out.printf("                        ║ %-5s ║ %-29s║ %-14s ║ %-13s ║", "ID", "FilmName", "Duration Time", "Type");
        System.out.println();
        System.out.println("                        ╠═══════╬══════════════════════════════╬════════════════╬═══════════════╣");
        for (int i = 0; i < listFilm.size(); i++) {
            if (i == (listFilm.size() - 1)) {
                System.out.println(listFilm.get(i).toView());
                System.out.println("                        ╚═══════╩══════════════════════════════╩════════════════╩═══════════════╝");
            } else {
                System.out.println(listFilm.get(i).toView());
                System.out.println("                        ╠═══════╬══════════════════════════════╬════════════════╬═══════════════╣");
            }
        }
        System.out.println();


    }

    //    public void getRevenueOfFilm() {//hiển th doanh thu
//        List<Double> revenue = new ArrayList<>();
//        double total = 0;
//        for (int i = 0; i < filmService.getAllFilms().size(); i++) {
//            revenue.add(orderService.getRevenueOfFilm(filmService.getFilmById(i + 1).getId()));
//            total += revenue.get(i);
//        }
//        System.out.println("                                                ╔══════════════════════════════╦════════════════╗");
//        System.out.printf("                                                ║ %-29s║ %14s ║", "Film Name", "Revenue");
//        System.out.println();
//        System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
//        for (int i = 0; i < revenue.size(); i++) {
//            if (i == (revenue.size()) - 1) {
//                System.out.printf("                                                ║ %-29s║ %14s ║", filmService.getFilmById(i + 1).getName(), CurrencyFormat.covertPriceToString(revenue.get(i))).println();
//                System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
//                System.out.printf("                                                ║ %-29s║ %14s ║", "TOTAL", CurrencyFormat.covertPriceToString(total)).println();
//                System.out.println("                                                ╚══════════════════════════════╩════════════════╝");
//            } else {
//                System.out.printf("                                                ║ %-29s║ %14s ║", filmService.getFilmById(i + 1).getName(), CurrencyFormat.covertPriceToString(revenue.get(i))).println();
//                System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
//            }
//        }
//        System.out.println();
//        System.out.println();
//
//    }
    public void getRevenueOfFilm() {
        List<Double> revenue = new ArrayList<>();
        double total = 0;

        List<Film> films = filmService.getAllFilms();
        for (Film film : films) {
            double filmRevenue = orderService.getRevenueOfFilm(film.getId());
            revenue.add(filmRevenue);
            total += filmRevenue;
        }

        System.out.println("                                                ╔══════════════════════════════╦════════════════╗");
        System.out.printf("                                                ║ %-29s║ %14s ║", "Film Name", "Revenue");
        System.out.println();
        System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
        for (int i = 0; i < revenue.size(); i++) {
            if (i == revenue.size() - 1) {
                System.out.printf("                                                ║ %-29s║ %14s ║", filmService.getFilmById(i + 1).getFilmName(), CurrencyFormat.covertPriceToString(revenue.get(i))).println();
                System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
                System.out.printf("                                                ║ %-29s║ %14s ║", "TOTAL", CurrencyFormat.covertPriceToString(total)).println();
                System.out.println("                                                ╚══════════════════════════════╩════════════════╝");
            } else {
                System.out.printf("                                                ║ %-29s║ %14s ║", filmService.getFilmById(i + 1).getFilmName(), CurrencyFormat.covertPriceToString(revenue.get(i))).println();
                System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
            }
        }
        System.out.println();
        System.out.println();
    }


//public Film getRevenueOfFilmByName() {
//    Scanner scanner = new Scanner(System.in);
//    System.out.print("Enter film name: ");
//    String filmName = scanner.nextLine().toUpperCase();
//
//    Film film = (Film) filmService.getAllFilms();
//    for (Film film : filmService.getAllFilms()) {
//        if (film.getFilmName().equals(filmName)) {
//            return film;
//        }
//    }
//    if (film == null) {
//        System.out.println("Film not found!");
//
//    }
//
//    double revenue = orderService.getRevenueOfFilm(film.getId());
//    System.out.println("                                     ╔════════════════════════╦════════════════╗");
//    System.out.printf("                                     ║ %-22s║ %14s ║", "Film Name", "Revenue");
//    System.out.println();
//    System.out.println("                                     ╠════════════════════════╬════════════════╣");
//    System.out.printf("                                     ║ %-22s║ %14s ║", film.getName(), CurrencyFormat.covertPriceToString(revenue));
//    System.out.println();
//    System.out.println("                                     ╚════════════════════════╩════════════════╝");
//    return film;
//}


    public void noChange() {
        System.out.println(" ⦿ Quay về menu thì nhập: 0 ⦿ ");
    }


    public void addNewFilm() {
        Film film = new Film();
        long id;
        while (true) {
            //Scanner scanner1 = new Scanner(System.in);

            System.out.println("Nhập ID phim: ");
            System.out.print("\t➥ ");

            try {
                id = Long.parseLong(scanner.nextLine());
                if (id > 0) {
                    if (filmService.checkIdFilm(id)) {
                        System.out.println("ID đã tồn tại!");
                    } else {
                        film.setId(id);
                        break;
                    }
                } else {
                    System.out.println("ID phải lớn hơn \"0\"");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID phải là một số!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String filmName;
        while (true) {
            System.out.println("Nhập tên phim:");
            System.out.print("\t➥ ");
            String nameNew = scanner.nextLine();
            if (filmService.existFilmName(nameNew)) {
                System.out.println("Phim đã tồn tại.Vui lòng kiểm tra lại! ");
            } else {
                filmName = nameNew;
                break;
            }
        }
        film.setFilmName(filmName);

        long durationTime;
        while (true) {
            System.out.println("Nhập thời lượng phim(>100 minute) : ");
            System.out.print("\t➥ ");
            try {
                durationTime = Long.parseLong(scanner.nextLine());
                if (durationTime > 100) {
                    film.setDurationTime(durationTime);
                    break;
                }
                System.out.println("Thời gian phải lớn hơn \"100\"");
            } catch (NumberFormatException e) {
                System.out.println("Thời lượng phải là số.");
            }
        }

        EType typeOfFilm = null;
        int choice;
        while (true) {
            boolean check = false;
            menuType();
            System.out.println("Chọn thể loại phim:");
            System.out.print("\t➥ ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        typeOfFilm = EType.ACTION;
                        check = true;
                        break;
                    case 2:
                        typeOfFilm = EType.CARTOON;
                        check = true;
                        break;
                    case 3:
                        typeOfFilm = EType.HORROR;
                        check = true;
                        break;
                    case 4:
                        typeOfFilm = EType.COMEDY;
                        check = true;
                        break;
                    case 5:
                        typeOfFilm = EType.SPORT;
                        check = true;
                        break;
                    case 6:
                        typeOfFilm = EType.DRAMA;
                        check = true;
                        break;
                    default:
                        System.out.println("Không đúng.Vui lòng chọn lại!");
                }
            } catch (Exception e) {
                System.out.println("Lựa chọn phải là một số!");
            }

            if (check) {
                film.setTypeOfFilm(typeOfFilm);
                break;
            }
        }
        film.setStatus(EStatus.NOW_SHOWING);

        // film = new Film(id,filmName,durationTime,typeOfFilm);
        filmService.add(film);
        System.out.println("Thêm phim mới thành công!");

        showAllFilms();
    }


    public void menuType() {
        System.out.println("╔═════TYPE════════════════════════╗");
        System.out.println("║    [1] ACTION      [4] COMEDY   ║ ");
        System.out.println("║    [2] CARTOON     [5] SPORT    ║ ");
        System.out.println("║    [3] HORROR      [6] DRAMA    ║  ");
        System.out.println("╚═════════════════════════════════╝");
    }

    public void setType(Film film) {
        menuType();
        System.out.println("Chọn thể loại phim: ");
        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    film.setTypeOfFilm(EType.ACTION);
                    break;
                case 2:
                    film.setTypeOfFilm(EType.CARTOON);
                    break;
                case 3:
                    film.setTypeOfFilm(EType.HORROR);
                    break;
                case 4:
                    film.setTypeOfFilm(EType.COMEDY);
                    break;
                case 5:
                    film.setTypeOfFilm(EType.SPORT);
                    break;
                case 6:
                    film.setTypeOfFilm(EType.DRAMA);
                    break;
                default:
                    System.out.println("Không đúng.Vui lòng nhập lại!");
            }
        } catch (Exception e) {
            System.out.println("Lựa chọn phải là một số!");
        }

    }

    public void deleteFilm() throws IOException {
        ShowTimeView showTimeView1 = new ShowTimeView();
        Scanner scanner1 = new Scanner(System.in);
        showAllFilms();


        System.out.println("Nhập ID muốn xóa: ");
        System.out.print("\t➥ ");
        long idFilm = 0;

        try {
            idFilm = Long.parseLong(scanner1.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ Vui lòng nhập lại!");
            deleteFilm();
            return;
        }
        Film film = filmService.findById(idFilm);
        if (film == null) {
            System.out.println("Không tìm thấy Film với ID : " + idFilm);
            deleteFilm();
            return;
        }
        // System.out.println("Bạn có chắc chắn muốn xóa suất chiếu này không? (Y/N)");
//        if (showTimeView1.confirmDelete()) {
//            filmService.deleteFilmById(idFilm);
//            System.out.println("Đã xóa suất chiếu có ID: " + idFilm);
//            showAllFilms();
//        }

        System.out.println("Bạn có chắc chắn muốn xóa suất chiếu này không? (Y/N)");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            filmService.deleteFilmById(idFilm);
            System.out.println("Đã xóa suất chiếu có ID: " + idFilm);
            showAllFilms();
        } else if (confirm.equalsIgnoreCase("N")) {
            System.out.println("OK. Quay lai menu.");
        } else {
            System.out.println("Tùy chọn không hợp lệ. Thao tác xóa đã bị hủy bỏ.");
        }

    }

    public static void main(String[] args) throws IOException {
        FilmView filmView1 = new FilmView();
        filmView1.getRevenueOfFilm();
    }


}
