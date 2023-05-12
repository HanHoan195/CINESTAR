package service;

import model.ERoom;
import model.Seat;
import model.ShowTime;
import repository.FileContext;
import repository.SeatRepository;

import java.util.ArrayList;
import java.util.List;

public class SeatService extends FileContext<Seat> {
    private SeatRepository seatRepository;

    public SeatService() {
        seatRepository = new SeatRepository();
    }
    public List<Seat> getAllSeats(){
        return seatRepository.getAll();
    }
    public List<Seat> getSeatsByIdRoom(ERoom room){
        long idRoom = room.getId();
        List<Seat> allSeats = seatRepository.getAll();
        List<Seat> seatsRoom = new ArrayList<>();
        for(Seat seat: allSeats){
            if(seat.geteRoom().getId() == idRoom){
                seatsRoom.add(seat);
            }
        }
        return seatsRoom;
    }
    public String getPostionSeatById(long idSeat){
        return seatRepository.findById(idSeat).getPosition();
    }
    public String getRoomById(long idSeat){
        return seatRepository.findById(idSeat).geteRoom().getName();
    }
    public long getIdSeat(ERoom room, String position){
        long idSeat = -1;
        List<Seat> allSeats = seatRepository.getAll();
        for(Seat seat : allSeats){
            if(seat.geteRoom() == room && seat.getPosition().equals(position) ){
                idSeat = seat.getId();
            }
        }
        return idSeat;
    }
    public Seat findSeatById(long id){
        return seatRepository.findById(id);
    }
    public long getIdSeatByRoom(String position, ShowTime showTime){
        long idShowTime = showTime.getId();
        ERoom room = showTime.getIdRoom();
        List<Seat> seatInRoom = getSeatsByIdRoom(room);
        for(Seat seat : seatInRoom){
            if(seat.getPosition().equalsIgnoreCase(position)){
                return seat.getId();
            }
        }
        return 0;
    }

    public Seat getSeatById(long seatId) {
        // Get all seats
        List<Seat> allSeats = seatRepository.getAll();

        // Find the seat with the given ID
        for (Seat seat : allSeats) {
            if (seat.getId() == seatId) {
                return seat;
            }
        }

        // No seat found with the given ID
        return null;
    }

}
