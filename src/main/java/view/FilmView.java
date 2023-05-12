package view;

import model.Film;
import model.ShowTime;
import repository.FileService;
import service.FilmService;
import service.OrderService;

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


    public FilmView(){
        filmService = new FilmService();
        orderService = new OrderService();
//        filmView = new FilmView();


    }
        public void showAllFilms() {//danh sách phim không có giờ chiếu
            List<Film> listFilm = filmService.getAllFilms();
            System.out.println("                        ╔═══════╦══════════════════════════════╦════════════════╦═══════════════╦═══════════════╗");
            System.out.printf("                        ║ %-5s ║ %-29s║ %-14s ║ %-13s ║ %-14s║", "ID", "FilmName", "Duration Time", "Type", "Status");
            System.out.println();
            System.out.println("                        ╠═══════╬══════════════════════════════╬════════════════╬═══════════════╬═══════════════╣");
            for (int i = 0; i < listFilm.size(); i++) {
                if (i == (listFilm.size() - 1)) {
                    System.out.println(listFilm.get(i).toView());
                    System.out.println("                        ╚═══════╩══════════════════════════════╩════════════════╩═══════════════╩═══════════════╝");
                } else {
                    System.out.println(listFilm.get(i).toView());
                    System.out.println("                        ╠═══════╬══════════════════════════════╬════════════════╬═══════════════╬═══════════════╣");
                }
            }
            System.out.println();
            System.out.println();


        }

        public void getRevenueOfFilm() {//hiển th doanh thu
            List<Double> revenue = new ArrayList<>();
            for (int i = 0; i < filmService.getAllFilms().size(); i++) {
                revenue.add(orderService.getRevenueOfFilm(filmService.getFilmById(i + 1).getId()));
            }
            System.out.println("                                                ╔══════════════════════════════╦════════════════╗");
            System.out.printf("                                                ║ %-29s║ %14s ║", "Film Name", "Revenue");
            System.out.println();
            System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
            for(int i = 0; i < revenue.size();i++){
                if(i == (revenue.size()) - 1){
                    System.out.printf("                                                ║ %-29s║ %14s ║",filmService.getFilmById(i+1).getName(),revenue.get(i)).println();
                    System.out.println("                                                ╚══════════════════════════════╩════════════════╝");
                } else{
                    System.out.printf("                                                ║ %-29s║ %14s ║",filmService.getFilmById(i+1).getName(),revenue.get(i)).println();
                    System.out.println("                                                ╠══════════════════════════════╬════════════════╣");
                }
            }
            System.out.println();
            System.out.println();
            menu.menuManager();
        }

    public void noChange() {
        System.out.println(" ⦿ Nếu không thay đổi gì thì nhập: 0 ⦿ ");
    }






}
