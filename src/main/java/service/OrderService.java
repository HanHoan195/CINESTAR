package service;

import model.Order;
import model.Seat;
import model.ShowTime;
import model.Ticket;
import repository.FileContext;
import repository.OrderRepository;
import repository.TicketRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderService extends FileContext<Ticket> {
    private OrderRepository orderRepository;
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private SeatService seatService;
    private ShowTimeService showTimeService;

    public OrderService() {
        orderRepository = new OrderRepository();
        seatService = new SeatService();
        showTimeService = new ShowTimeService();
        ticketService = new TicketService();
    }

    public List<Order> getAllOrder() {
        return orderRepository.getAll();
    }

    public Order getOrderByIdOrder(long idOrder) {
        return orderRepository.findById(idOrder);
    }

    public void addNewOrder(Order order) {
        orderRepository.add(order);
    }

    public List<Seat> emtySeats(String position) {
        List<Seat> soldSeat = new ArrayList<>();
        return soldSeat;
    }
    public List<Ticket> getTicketListById(long idOrder) {
        return ticketService.getTicketListByIdOrder(idOrder);
    }

    public List<Seat> getOccupiedSeatByShowTimeId(long idShowTime) {
        List<Ticket> ticketList = ticketService.getAllTickets();
        List<Order> allOrders = getAllOrder();
        List<Ticket> tickets = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++){
            for (int j = 0; j < ticketList.size(); j++) {
                if(allOrders.get(i).getId() == ticketList.get(j).getIdOrder()) {
                    tickets.add(ticketList.get(j));
                }
            }
        }
        List<Seat> occupiedSeatList = new ArrayList<>();
            for (Ticket ticket : tickets) {
                if (ticket.getShowTimeId() == idShowTime) {
                    occupiedSeatList.add(seatService.findSeatById(ticket.getIdSeat()));
                }
            }
        return occupiedSeatList;
    }

    public void updateOrder(Order order) {
        orderRepository.updateById(order.getId(), order);
    }
    public double getRevenueOfFilm( long idFilm) {
        List<Double> total = new ArrayList<>();
        List<Order> allOrders = getAllOrder();
        double revenue = 0;
        for (int i = 0; i < allOrders.size(); i++) {
            for (Ticket ticket : allOrders.get(i).getOrderitemList()) {
                if (showTimeService.findFilmByShowTimeId(ticket.getShowTimeId()) == idFilm) {
                    revenue += ticket.getTotalPrice();
                }
            }
        }
        return revenue;
    }
    public List<Order> findOrderListByIdCustomer(long idCustomer){
        List<Order> allOrders = getAllOrder();
        List<Order> result = new ArrayList<>();
        for(Order order : allOrders){
            if(order.getIdCustomer() == idCustomer){
                result.add(order);
            }
        }
        return result;
    }
    public void addNewOccupiedSeat(ShowTime showTime, long idSeat){
        List<Seat> occupiedSeats = showTimeService.findById(showTime.getId()).getOccupiedSeats();
        occupiedSeats.add(seatService.findSeatById(idSeat));
        showTime.setOccupiedSeats(occupiedSeats);
        int emptySeat = showTime.getEmptySeat();
        showTime.setEmptySeat(emptySeat);
        showTimeService.findById(showTime.getId()).update(showTime);
    }
}
