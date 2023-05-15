package service;

import model.Ticket;
import repository.FileService;
import repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

public class TicketService {
    private TicketRepository ticketRepository;
    FileService fileService = new FileService();

    public TicketService() {
        ticketRepository = new TicketRepository();
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.getAll();
    }

    public void addNewTicket(Ticket ticket) {
        ticketRepository.add(ticket);
    }

    public List<Ticket> getTicketListByIdOrder(long idOrder) {
        List<Ticket> allTicket = ticketRepository.getAll();
        List<Ticket> ticketListByIdOrder = new ArrayList<>();
        for (int i = 0; i < allTicket.size(); i++) {
            if (allTicket.get(i).getIdOrder() == idOrder) {
                ticketListByIdOrder.add(allTicket.get(i));
            }
        }
        return ticketListByIdOrder;
    }

    public Ticket findTicketById(long idTicket) {
        return ticketRepository.findById(idTicket);
    }

    public void delete(Ticket ticket) {
        ticketRepository.deleteById(ticket.getId());
    }


}
