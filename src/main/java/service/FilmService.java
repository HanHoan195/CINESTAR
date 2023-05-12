package service;

import model.Film;
import model.ShowTime;
import repository.FileService;
import repository.FilmRepository;
import repository.FilmUpdateRepo;
import utils.CSVUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmService {
    List<Film> fimlList = new ArrayList<>();
    public static String path = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\listphim.csv";

    private FilmRepository filmRepository;
    private FilmUpdateRepo filmUpdateRepo;

    public FilmService() {
        filmRepository = new FilmRepository();
        filmUpdateRepo = new FilmUpdateRepo();
    }

    public List<Film> getAllFilms() {
        return filmRepository.getAll();
    }

    public List<Film> getAllFilmsUpdate() {
        return filmUpdateRepo.getAll();
    }

    public Film getFilmById(long id) {
        return filmRepository.findById(id);
    }

    public long findDurationTimeById(long id) {
        return filmRepository.findById(id).getDurationTime();
    }


    public String findFilmById(long id) {
        return filmRepository.findById(id).getName();
    }

    public int checkIdFilm(long id) throws IOException {
        return filmRepository.checkID( id);
    }

    public Film findById(long idFilm) {
        Film[] films = getAllFilms().toArray(new Film[0]);
        for (Film film : films) {
            if (film.getId() == idFilm) {
                return film;
            }
        }
        return null;
    }

}




