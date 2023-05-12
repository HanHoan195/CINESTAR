package model;

import repository.IModel;
import service.SeatService;
import service.ShowTimeService;
import utils.DateUtils;

import java.util.Date;

public class Ticket implements IModel<Ticket> {
    private long id;
    private long idOrder;
    private Date soldDate;
    private double totalPrice;
    private long showTimeId;
    private static double ticketPrice = 45000;
    private long idSeat;
    private long customerId;

    private String name;
    private String phone;
    private static ShowTimeService showTimeService = new ShowTimeService();


    private static SeatService seatService = new SeatService();



    public Ticket() {
    }

    public Ticket(long id, long showTimeId, long idSeat, long customerId) {
        this.id = id;
        this.showTimeId = showTimeId;
        this.idSeat = idSeat;
        this.customerId = customerId;
    }

    public Ticket(long id, long showTimeId, long idSeat) {
        this.id = id;
        this.showTimeId = showTimeId;
        this.idSeat = idSeat;
    }

    public Ticket(long id, long idOrder, Date soldDate, double totalPrice, long showTimeId, long idSeat, long customerId) {
        this.id = id;
        this.idOrder = idOrder;
        this.soldDate = soldDate;
        this.totalPrice = totalPrice;
        this.showTimeId = showTimeId;
        this.idSeat = idSeat;
        this.customerId = customerId;
    }

    public Ticket(long id, long id1, String name, String phone) {
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date soldDate) {
        this.soldDate = soldDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getShowTimeId() {
        return showTimeId;
    }

    public void setShowTimeId(long showTimeId) {
        this.showTimeId = showTimeId;
    }

    public static double getTicketPrice() {
        return ticketPrice;
    }

    public static void setTicketPrice(double ticketPrice) {
        Ticket.ticketPrice = ticketPrice;
    }

    public long getIdSeat() {
        return idSeat;
    }

    public void setIdSeat(long idSeat) {
        this.idSeat = idSeat;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public static ShowTimeService getShowTimeService() {
        return showTimeService;
    }

    public static void setShowTimeService(ShowTimeService showTimeService) {
        Ticket.showTimeService = showTimeService;
    }

    public static SeatService getSeatService() {
        return seatService;
    }


    public static void setSeatService(SeatService seatService) {
        Ticket.seatService = seatService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void update(Ticket obj) {
        this.id = obj.getId();
        this.idOrder = obj.getIdOrder();
        this.showTimeId = obj.getShowTimeId();
        this.idSeat = obj.getIdSeat();
        this.totalPrice = obj.getTotalPrice();

    }

    @Override
    public Ticket parseData(String line) {
        Ticket ticket = new Ticket();

        //long id, long idOrder, long showTimeId, long idSeat, total
        String[] itemInfo = line.split(",");
        long id = Long.parseLong(itemInfo[0]);
        long idOrder = Long.parseLong(itemInfo[1]);
        long showTimeId = Long.parseLong(itemInfo[2]);
        long idSeat = Long.parseLong(itemInfo[3]);
        double total = Double.parseDouble(itemInfo[4]);

        ticket.setId(id);
        ticket.setIdOrder(idOrder);
        ticket.setShowTimeId(showTimeId);
        ticket.setIdSeat(idSeat);
        ticket.setTotalPrice(total);
        return ticket;
    }

    public double getTotal() {
        double total;
        double surchargeFormat = showTimeService.findById(this.showTimeId).getFormat().getSurcharge();
        total = ticketPrice + surchargeFormat;
        return total;
    }

    public String toView() {
        String filmName = showTimeService.getFilmNameById(this.showTimeId);
        Date startTime = showTimeService.findById(this.showTimeId).getStartTime();
        String start = DateUtils.convertDateToString(startTime);
        String roomName = seatService.getRoomById(this.idSeat);
        String position = seatService.getPostionSeatById(this.idSeat);
        String format = showTimeService.findById(this.showTimeId).getFormat().getNameFormat();
        return String.format("║%7s║%-30s║%-20s║%-7s║%-7s║%-7s║%-20s║",
                this.id, filmName, start, roomName, position, format, this.totalPrice);
    }
    public String simpleView() {
        Film film = showTimeService.getFilmByShowTimeId(this.showTimeId);
        String filmName = film.getName();
        Date startTime = showTimeService.findById(this.showTimeId).getStartTime();
        String start = DateUtils.convertDateToString(startTime);
        String roomName = seatService.getRoomById(this.idSeat);
        String position = seatService.getPostionSeatById(this.idSeat);
        return String.format("                                  ║%7s|%-30s|%-20s|%-7s|%-7s|%-15s║", this.id, filmName, start, roomName, position, this.totalPrice);
    }

    @Override
    public String toString() {
        //long id, long idOrder, long showTimeId, long idSeat,double tota
        return String.format("%s,%s,%s,%s,%s", this.id, this.idOrder, this.showTimeId, this.idSeat, this.totalPrice);
    }

    public void printTicket() {
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        System.out.println(">>>CINEMA  |                                <<<<");
        System.out.println(">>>TICKET  |                                <<<<");
        System.out.println(">>>┌───────────────────────────────────────┐<<<<");
        System.out.printf(">>> %s      |    <<<<", this.id);

    }

}

