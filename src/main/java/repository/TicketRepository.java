package repository;

import model.Ticket;

public class TicketRepository extends FileContext<Ticket> {
    public TicketRepository() {
        filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\ticket.csv";
        tClass = Ticket.class;
    }
}
