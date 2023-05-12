package model;

import repository.IModel;

public class Film implements IModel<Film> {
    private long id;
    private String filmName;
    private long durationTime;
    private EType typeOfFilm;
    private EStatus status;


    public Film() {
    }

    public Film(long id, String filmName, long showTime, EType typeOfFilm, EStatus status) {
        this.id = id;
        this.filmName = filmName;
        this.durationTime = showTime;
        this.typeOfFilm = typeOfFilm;
        this.status = status;
    }

    public long getId() {
        return id;
    }


    public String getName() {
        return filmName;
    }

    @Override
    public void update(Film obj) {
        this.id = obj.id;
        this.filmName = obj.filmName;
        this.durationTime = obj.durationTime;
        this.typeOfFilm = obj.typeOfFilm;
        this.status = obj.status;


    }

    @Override
    public Film parseData(String line) {
        //Film film = new Film();
        String [] itemInfo = line.split(",");
        long id = Long.parseLong(itemInfo[0]);
        long durationTime = Long.parseLong(itemInfo[2]);
        EType typeOfFilm = EType.getTypeByName(itemInfo[3]);
        EStatus status = EStatus.getStatusByName(itemInfo[4]);


        Film film = new Film(id,itemInfo[1],durationTime,typeOfFilm,status);
        return film;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public long getShowTime() {
        return durationTime;
    }

    public void setShowTime(long showTime) {
        this.durationTime = showTime;
    }

    public EType getTypeOfFilm() {
        return typeOfFilm;
    }

    public void setTypeOfFilm(EType typeOfFilm) {
        this.typeOfFilm = typeOfFilm;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public String toView() {
        return String.format("                        ║ %-5s ║ %-29s║ %-14s ║ %-13s ║ %-14s║",
                id, filmName, durationTime + " minutes", typeOfFilm, status);
    }
}

