package model;


import repository.IModel;
import service.FilmService;
import service.OrderService;
import service.TicketService;
import utils.CurrencyFormat;
import utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements IModel<Order> {
    private long id;
    private long idCustomer;
    private String customerName;
    private double total;
    private Date createDate;
    private List<Ticket> orderitemList ;
    FilmService filmService = new FilmService();

    public Order() {

    }

    public Order(long id, long idCustomer, String customerName, double total, Date createDate, List<Ticket> orderitemList) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.customerName = customerName;
        this.total = total;
        this.createDate = createDate;
        this.orderitemList = orderitemList;
    }
    public Order(long id, String customerName, Date createDate, List<Ticket> orderitemList) {
        this.id = id;
        this.customerName = customerName;
        this.total = total();
        this.createDate = createDate;
        this.orderitemList = orderitemList;
    }

    public Order(long id, String customerName, Date createDate) {
        this.id = id;
        this.customerName = customerName;
        this.createDate = createDate;
    }

    public Order(long id, String customerName, double total, Date createDate, List<Ticket> orderitemList) {
        this.id = id;
        this.customerName = customerName;
        this.total = total;
        this.createDate = createDate;
        this.orderitemList = orderitemList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Ticket> getOrderitemList() {
        TicketService ticketService = new TicketService();
        List<Ticket> ticketList;
        return ticketList = ticketService.getAllTickets();
    }

    public void setOrderitemList(List<Ticket> orderitemList) {
        this.orderitemList = orderitemList;
    }

    public void addTicket(Ticket ticket) {
        orderitemList.add(ticket);
    }
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void update(Order obj) {
        this.id = id;
        this.idCustomer = idCustomer;
        this.customerName = customerName;
        this.total = obj.getTotal();
        this.createDate =obj.getCreateDate();

    }

    @Override
    public Order parseData(String line) {
        TicketService ticketService = new TicketService();
        Order order = new Order();
        String[] itemInfo = line.split(",");
        long id =Long.parseLong(itemInfo[0]);
        long idCustomer = Long.parseLong(itemInfo[1]);
        String customerName = itemInfo[2];
        double total = Double.parseDouble(itemInfo[3]);
        Date createDate =DateUtils.parseDate(itemInfo[4]);
        List<Ticket> orderitemList = ticketService.getTicketListByIdOrder(id);

        order.setId(id);
        order.setIdCustomer(idCustomer);
        order.setCustomerName(customerName);
        order.setCreateDate(createDate);
        order.setTotal(total);
        order.setOrderitemList(orderitemList);
        return order;
    }
    public double total(){
        double totalOrder = 0;
        for (Ticket ticket : this.getOrderitemList()) {
            totalOrder += ticket.getTotal();
        }
        return totalOrder;
    }

    public String toView() {
        //long id, String customerName, double total, Date createDate, List<Ticket> orderitemList
        String create = DateUtils.convertDateToString(this.createDate);
        int amountTicket = orderitemList.size();
        return String.format("                        ║%7s║%-30s║%-20s║%-20s║%-7s║",
                this.id, this.customerName, amountTicket, this.total, create);
    }
    public String oderView(long id) {

        return String.format("            ║ %-30s║ %19s ║ %14s ║", filmService.getFilmById(id).getFilmName() ,  DateUtils.convertDateToString(this.createDate), CurrencyFormat.covertPriceToString(this.total));
    }

    @Override
    public String toString() {

        String date = DateUtils.convertDateToString(this.createDate);
        return String.format("%s,%s,%s,%s,%s", this.id, this.idCustomer, this.customerName, this.total, date);
    }
}
