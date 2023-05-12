package repository;

import model.ShowTime;

public class ShowTimeRepository extends FileContext<ShowTime>{
    public ShowTimeRepository(){
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\showtime.csv";
        tClass = ShowTime.class;
    }
}
