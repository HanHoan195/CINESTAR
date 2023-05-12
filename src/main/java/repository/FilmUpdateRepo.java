package repository;

import model.Film;

public class FilmUpdateRepo extends FileContext<Film> {
    public FilmUpdateRepo() {
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\filmupdate.csv";
        tClass = Film.class;
    }
}
