package repository;

import model.Film;

public class FilmRepository extends FileContext<Film> {
    public FilmRepository() {
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\listphim.csv";
        tClass = Film.class;
    }
}
