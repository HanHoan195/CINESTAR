package repository;

import model.Seat;

public class SeatRepository extends FileContext<Seat> {
    public SeatRepository() {
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\seat.csv";
        tClass = Seat.class;
    }
}
