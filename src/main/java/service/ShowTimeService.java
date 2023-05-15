package service;

import model.Film;
import model.Seat;
import model.ShowTime;
import model.Ticket;
import repository.FileContext;
import repository.ISearch;
import repository.ShowTimeRepository;

import java.util.ArrayList;
import java.util.List;

public class ShowTimeService extends FileContext<ShowTime> {
    private ShowTimeRepository showTimeRepository;
    private FilmService filmService;
    private TicketService ticketService;
    private SeatService seatService;

    List<ShowTime> showTimeList = new ArrayList<>();

    public ShowTimeService() {
        showTimeRepository = new ShowTimeRepository();
        filmService = new FilmService();
        ticketService = new TicketService();
        seatService = new SeatService();
    }

    public List<ShowTime> getAllShowTimes() {
        List<ShowTime> showTimeList = showTimeRepository.getAll();
        return showTimeList;
    }

    public void add(ShowTime showTime) {
        showTimeRepository.add(showTime);
    }

    public ShowTime findById(long id) {
        return showTimeRepository.findById(id);
    }

    public void deleteById(long id) {
        showTimeRepository.deleteById(id);
    }

    public void updateShowTimeById(long id, ShowTime showTime) {
        showTimeRepository.updateById(id, showTime);
    }

    public List<ShowTime> searchByName(String filmName) {
        ISearch<ShowTime> iSearch = new ISearch<ShowTime>() {
            @Override
            public boolean searchByName(ShowTime obj, String name) {
                String nameOfFilm = filmService.findFilmById(obj.getIdFilm());
                boolean checkSearch = nameOfFilm.toUpperCase().contains(name.toUpperCase());
                if (checkSearch) {
                    return true;
                }
                return false;
            }
        };
        return showTimeRepository.searchByName(filmName, iSearch);
    }

    public String getFilmNameById(long idFilm) {
        return filmService.findFilmById(idFilm);
    }

    public Film getFilmByShowTimeId(long idShowTime) {
        ShowTime showTime = findById(idShowTime);
        long idFilm = showTime.getIdFilm();
        return filmService.getFilmById(idFilm);
    }

    public long findFilmByShowTimeId(long idShowTime) {
        ShowTime showTime = findById(idShowTime);
//        long idFilm = 0;
//        if(showTime != null) {
//            idFilm = showTime.getIdFilm();
//        }
        long idFilm = showTime.getIdFilm();
        return idFilm;
    }

    public boolean checkEmptySeat(long idShowtime) {
        ShowTime showTime = showTimeRepository.findById(idShowtime);
        if (showTime.getEmptySeat() >= 1) {
            return true;
        }
        return false;
    }

    public List<Seat> getOccupiedSeatListByIdTicket(ShowTime showTime) {
        List<Ticket> ticketList = ticketService.getAllTickets();
        List<Seat> occupiedSeatList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getShowTimeId() == showTime.getId()) {
                Seat seat = seatService.findSeatById(ticket.getIdSeat());
                occupiedSeatList.add(seat);
            }
        }
        return occupiedSeatList;
    }

    public void saveData(long idShowTime, int emptySeat, List<Seat> occupiedSeat) {
        List<ShowTime> list = getAllShowTimes();
        for (ShowTime showTime : list) {
            if (showTime.getId() == idShowTime) {
                showTime.setEmptySeat(emptySeat);
                showTime.setOccupiedSeats(occupiedSeat);
            }
        }
        showTimeRepository.addList(list);
    }

    public boolean existRoom(long idRoom) {
        //getAllShowTimes();
        showTimeList = getAllShowTimes();
        for (ShowTime showTime : showTimeList) {
            if (showTime.getIdRoom().getId() == idRoom) {
                return true;
            }
        }
        return false;
    }

    public boolean existFilm(long idRoom, long idShowtime) {
        getAllShowTimes();
        for (ShowTime showTime : showTimeList) {
            if (showTime.getId() == idShowtime && showTime.getIdRoom().getId() == idRoom) {
                return true;
            }
        }
        return false;
    }

    public ShowTime getShowtimeById(long id) {
        List<ShowTime> showTimes = getAllShowTimes();
        for (ShowTime showTime : showTimes) {
            if (showTime.getId() == id) {
                return showTime;
            }
        }
        return null;
    }

    public void setOccupiedSeats(long showTimeId, List<Seat> seatList) {
        ShowTime showTime = findById(showTimeId);
        if (showTime != null) {
            showTime.setOccupiedSeats(seatList);
        }
        // Optionally, you could throw an exception or print an error message if the show time doesn't exist
    }


}
