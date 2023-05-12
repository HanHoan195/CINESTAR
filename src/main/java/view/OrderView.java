package view;

import model.*;
import repository.ShowTimeRepository;
import service.*;
import utils.DateUtils;

import java.util.*;

public class OrderView {
//    private ShowTimeView showTimeView;
//    private OrderService orderService;
//    private ShowTimeService showTimeService;
//    private SeatService seatService;
//    private SeatView seatView;
//    private ShowTimeRepository showTimeRepository;
//    private TicketService ticketService;
//    private FilmView filmView;
//     private CustomerService customerService ;
//    private CustomerView customerView;

    private static int countEnter = 3;


//    public OrderView() {
        ShowTimeRepository showTimeRepository = new ShowTimeRepository();

        OrderService orderService = new OrderService();
        ShowTimeService showTimeService = new ShowTimeService();
        CustomerService customerService = new CustomerService();
        CustomerView customerView = new CustomerView();
        SeatService seatService = new SeatService();
        SeatView seatView = new SeatView();
        TicketService ticketService = new TicketService();
        FilmView filmView = new FilmView();
        Scanner scanner = new Scanner(System.in);
//    }

    public OrderView() {
    }

    public void addNewOrder() {
        Order order = new Order();
        long idOrder = System.currentTimeMillis() % 1000;
        Date createDate = new Date();
        long idCustomer = checkExistCustomer();
        String customerName = customerService.findById(idCustomer).getName();

        order.setId(idOrder);
        order.setIdCustomer(idCustomer);
        order.setCustomerName(customerName);
        order.setCreateDate(createDate);
        orderService.addNewOrder(order);
        List<Ticket> ticketList = new ArrayList<>();
        boolean continueToAdd;
        do {
            Ticket ticket = addNewTicket();
            if (ticket != null) {
                ticket.setIdOrder(idOrder);
                ticketService.addNewTicket(ticket);
                ticketList.add(ticket);
            }
            continueToAdd = checkContinueAdd();
        } while (continueToAdd);
        //long id, String customerName, double total, Date createDate, List<Ticket> orderitemList
        if (ticketList.size() != 0) {
            order.setOrderitemList(ticketList);
            order.setTotal(order.total());
            orderService.updateOrder(order);
            customerService.addNewOrder(order);
            displayOrderDetail(idOrder);
        } else {
            System.out.println("Đơn đặt hàng của bạn trống!!!");
            orderService.deleteById(idOrder);
        }
    }

    public void displayOrderDetail(long idOrder) {
        Order order = orderService.getOrderByIdOrder(idOrder);
        if (order == null) {
            System.out.println("Không tìm thấy đơn đặt hàng này");
            return;
        }
        List<Ticket> tickets = order.getOrderitemList();
        System.out.println("                                  ╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "ID Order", order.getId()).println();
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "Customer's name", order.getCustomerName()).println();
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "Create Date", DateUtils.convertDateToString(order.getCreateDate())).println();
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");
        System.out.printf("                                  ║%7s|%-30s|%-20s|%-7s|%-7s|%-15s║", "ID", "Film", "Start", "Room", "Seat", "Price").println();
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");

        for (Ticket ticket : tickets) {
            System.out.println(ticket.simpleView());
        }
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");
        System.out.printf("                                  ║%20s %53s %-16s║", "Total", " ", order.getTotal()).println();
        System.out.printf("                                  ║%91s║", " ").println();
        System.out.printf("                                  ║%20s %39s %-30s║", " ", "THANKS FOR SHOPPING!!!", " ").println();
        System.out.println("                                  ╚═══════════════════════════════════════════════════════════════════════════════════════════╝");
    }

    public long checkExistCustomer() {
        System.out.println("Nhập tên khách hàng:");
        System.out.print("\t➥ ");
        String customerName = scanner.nextLine();
        List<User> result = customerService.searchByName(customerName);
        if (result.size() != 0) {
            customerView.displayAllCustomer(result);
            System.out.println("Chọn ID khách hàng:");
            System.out.print("\t➥ ");
            long idCustomer = Long.parseLong(scanner.nextLine());
            for (User customer : result) {
                if (customer.getId() == idCustomer) {
                    return customer.getId();
                }
            }
        }
        return 222;
    }
    public boolean checkContinueAdd() {
        boolean checkContinue = false;
        do {
            System.out.println("Bạn muốn tiếp tục không?");
            System.out.print("\t➥ ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    checkContinue = true;
            }
        } while (checkContinue);
        return false;
    }

    public Ticket addNewTicket() { //thêm vé
        ShowTimeView showTimeView = new ShowTimeView();

//        Ticket ticket = new Ticket();
//        long id = System.currentTimeMillis() % 1000;
//        //long id, Date soldDate, long showTimeId, long idSeat, long customerId
//        showTimeView.displayAllShowTimes();
//        long idShowTime;
//        //còn ghế: true ; không: false
//        boolean emptySeat = false;
//        int count = 0;
//        do {
//            System.out.println("Choose ID's showtime you want to buy ticket");
//            idShowTime = Long.parseLong(scanner.nextLine());
//            boolean overdue = checkShowTimeOverdue(idShowTime);
//            if (overdue == true) {
//                System.out.println("This show is overdue.");
//                return null;
//            }
//            emptySeat = showTimeService.checkEmptySeat(idShowTime);
//            if (!emptySeat) {
//                System.out.println("This showtime don't have empty seat. Please choose another showtime");
//            }
//            // giới hạn số lần nhập
//            count++;
//            if (count == countEnter) {
//                boolean checkContinue = checkContinueAddTicket();
//                if (!checkContinue) {
//                    return null;
//                }
//            }
//        } while (!emptySeat);
//        // kiểm tra seat
//        String seat;
//        count = 0;
//        long idSeat;
//        do {
//            if (showTimeService.findById(idShowTime).getOccupiedSeats() == null) {
//                seatView.displayRoom(showTimeService.findById(idShowTime).getIdRoom());
//            } else {
//                seatView.displayRoomByShowTime(showTimeService.findById(idShowTime));
//            }
//            System.out.println("Please choose your seat");
//            seat = scanner.nextLine().toUpperCase();
//            if (!checkOccupiedSeat(idShowTime, seat)) {
//                System.out.println("Occupied Seat!!! Please choose again");
//            }
//            count++;
//            if (count == countEnter) {
//                boolean checkContinue = checkContinueAddTicket();
//                if (!checkContinue) {
//                    return null;
//                }
//            }
//        } while (!checkOccupiedSeat(idShowTime, seat));
//        ERoom room = showTimeService.findById(idShowTime).getIdRoom();
//        idSeat = seatService.getIdSeatByRoom(seat, showTimeService.findById(idShowTime));
//        //long id, long showTimeId, long idSeat
//        ticket.setId(id);
//        ticket.setShowTimeId(idShowTime);
//        ticket.setIdSeat(idSeat);
//        ticket.setTotalPrice(ticket.getTotal());
//        if (ticket != null) {
//            List<Seat> occupiedSeat = showTimeService.findById(ticket.getShowTimeId()).getOccupiedSeats();
//            if (occupiedSeat == null) {
//                List<Seat> seatList = new ArrayList<>();
//                seatList.add(seatService.findSeatById(idSeat));
//                occupiedSeat = seatList;
//            } else {
//                occupiedSeat.add(seatService.findSeatById(idSeat));
//            }
//            int countEmptySeat = (showTimeService.findById(ticket.getShowTimeId()).getEmptySeat()) - 1;
//            showTimeService.saveData(ticket.getShowTimeId(), countEmptySeat, occupiedSeat);
////            orderService.addNewOccupiedSeat(showTimeService.findById(ticket.getShowTimeId()), ticket.getIdSeat());
//        }
//        return ticket;


        Ticket ticket = new Ticket();
        long id = System.currentTimeMillis() % 1000;
        showTimeView.displayAllShowTimes();
        long idShowTime;
        boolean emptySeat = false;
        int count = 0;
        do {
            System.out.println("Chọn ID suât chiếu bạn muốn mua vé:");
            System.out.print("\t➥ ");
            idShowTime = Long.parseLong(scanner.nextLine());
            boolean overdue = checkShowTimeOverdue(idShowTime);
            if (overdue == true) {
                System.out.println("Suất chiếu đã quá hạn!.");
                return null;
            }
            emptySeat = showTimeService.checkEmptySeat(idShowTime);
            if (!emptySeat) {
                System.out.println("Suất chiếu này không còn ghế trống vui lòng chọn suất khác!");
            }
            count++;
            if (count == countEnter) {
                boolean checkContinue = checkContinueAddTicket();
                if (!checkContinue) {
                    return null;
                }
            }
        } while (!emptySeat);

        String seat;
        count = 0;
        long idSeat;
        do {
            if (showTimeService.findById(idShowTime).getOccupiedSeats() == null) {
                seatView.displayRoom(showTimeService.findById(idShowTime).getIdRoom());
            } else {
                seatView.displayRoomByShowTime(showTimeService.findById(idShowTime));
            }
            System.out.println("Vui lòng chọn ghế: ");
            System.out.print("\t➥ ");
            seat = scanner.nextLine().toUpperCase();
            if (!checkOccupiedSeat(idShowTime, seat)) {
                System.out.println("Ghế đã được chọn!Vui lòng chọn lại!");
            }
            count++;
            if (count == countEnter) {
                boolean checkContinue = checkContinueAddTicket();
                if (!checkContinue) {
                    return null;
                }
            }
        } while (!checkOccupiedSeat(idShowTime, seat));

        ERoom room = showTimeService.findById(idShowTime).getIdRoom();
        idSeat = seatService.getIdSeatByRoom(seat, showTimeService.findById(idShowTime));

        ticket.setId(id);
        ticket.setShowTimeId(idShowTime);
        ticket.setIdSeat(idSeat);
        ticket.setTotalPrice(ticket.getTotal());
        if (ticket != null) {
            List<Seat> occupiedSeat = showTimeService.findById(ticket.getShowTimeId()).getOccupiedSeats();
            if (occupiedSeat == null) {
                List<Seat> seatList = new ArrayList<>();
                seatList.add(seatService.findSeatById(idSeat));
                occupiedSeat = seatList;
            } else {
                occupiedSeat.add(seatService.findSeatById(idSeat));
            }
            int countEmptySeat = (showTimeService.findById(ticket.getShowTimeId()).getEmptySeat()) - 1;
            showTimeService.saveData(ticket.getShowTimeId(), countEmptySeat, occupiedSeat);
        }

        return ticket;

    }

    public boolean checkShowTimeOverdue(long idShow) {
        Date now = new Date();
        if (showTimeService.findById(idShow).getStartTime().before(now)) {
            return true;
        }
        return false;
    }

    public boolean checkOccupiedSeat(long idShowTime, String selectedSeat) {
        long idSeat = seatService.getIdSeatByRoom(selectedSeat, showTimeService.findById(idShowTime));
        List<Seat> occupiedSeat = showTimeService.findById(idShowTime).getOccupiedSeats();
        if (occupiedSeat == null) {
            return true;
        }
        for (Seat seat : occupiedSeat) {
            if (seat.getId() == idSeat) {
                return false;
            }
        }
        return true;
    }

    public boolean checkContinueAddTicket() {
        System.out.println("Nhập [Y] để tiếp tục hoặc nếu muốn hủy nhập phím bất kỳ để thoát!");
        System.out.print("\t➥ ");
        String choose = scanner.nextLine();
        switch (choose) {
            case "Y":
                return true;
            default:
                return false;
        }
    }



//    public Ticket buyTicket() {
//        Ticket ticket = new Ticket();
//        long id = System.currentTimeMillis() % 1000;
//        //long id, Date soldDate, long showTimeId, long idSeat, long customerId
//        showTimeView.displayAllShowTimes();
//        long idShowTime;
//        //còn ghế: true ; không: false
//        boolean emptySeat;
//        int count = 0;
//        do {
//            System.out.println("Choose ID's showtime you want to buy ticket");
//            idShowTime = Long.parseLong(scanner.nextLine());
//            boolean overdue = checkShowTimeOverdue(idShowTime);
//            if (overdue == true) {
//                System.out.println("This show is overdue.");
//                return null;
//            }
//            emptySeat = showTimeService.checkEmptySeat(idShowTime);
//            if (!emptySeat) {
//                System.out.println("This showtime don't have empty seat. Please choose another showtime");
//            }
//            // giới hạn số lần nhập
//            count++;
//            if (count == countEnter) {
//                boolean checkContinue = checkContinueAddTicket();
//                if (!checkContinue) {
//                    return null;
//                }
//            }
//        } while (!emptySeat);
//
//
//        // Nhập thông tin người dùng
//        System.out.println("Please enter your name:");
//        String name = "";
//        try {
//            name = scanner.nextLine();
//        } catch (NoSuchElementException e) {
//            System.out.println("Error: Scanner was closed before entering name.");
//            return null;
//        }
//        System.out.println("Please enter your email:");
//        String email = "";
//        try {
//            email = scanner.nextLine();
//        } catch (NoSuchElementException e) {
//            System.out.println("Error: Scanner was closed before entering email.");
//            return null;
//        }
//
//        Customer customer = new Customer(name, email);
//        long customerId = customerService.addNewCustomer(customer);
//
//        // kiểm tra seat
//        String seat;
//        count = 0;
//        long idSeat;
//        do {
//            if (showTimeService.findById(idShowTime).getOccupiedSeats() == null) {
//                seatView.displayRoom(showTimeService.findById(idShowTime).getIdRoom());
//            } else {
//                seatView.displayRoomByShowTime(showTimeService.findById(idShowTime));
//            }
//            System.out.println("Please choose your seat");
//            seat = scanner.nextLine().toUpperCase();
//            if (!checkOccupiedSeat(idShowTime, seat)) {
//                System.out.println("Occupied Seat!!! Please choose again");
//            }
//            count++;
//            if (count == countEnter) {
//                boolean checkContinue = checkContinueAddTicket();
//                if (!checkContinue) {
//                    return null;
//                }
//            }
//        } while (!checkOccupiedSeat(idShowTime, seat));
//        ERoom room = showTimeService.findById(idShowTime).getIdRoom();
//        idSeat = seatService.getIdSeatByRoom(seat, showTimeService.findById(idShowTime));
//        //long id, long showTimeId, long idSeat
//        ticket.setId(id);
//        ticket.setShowTimeId(idShowTime);
//        ticket.setIdSeat(idSeat);
//        ticket.setCustomerId(customerId);
//        ticket.setTotalPrice(ticket.getTotal());
//        if (ticket != null) {
//            List<Seat> occupiedSeat = showTimeService.findById(ticket.getShowTimeId()).getOccupiedSeats();
//            if (occupiedSeat == null) {
//                List<Seat> seatList = new ArrayList<>();
//                seatList.add(seatService.findById(idSeat));
//                showTimeService.setOccupiedSeats(ticket.getShowTimeId(), seatList);
//            } else {
//                occupiedSeat.add(seatService.findById(idSeat));
//                showTimeService.setOccupiedSeats(ticket.getShowTimeId(), occupiedSeat);
//            }
//        }
//        return ticket;
//    }



        public static void main(String[] args)  {
        OrderView orderView = new OrderView();
        orderView.addNewOrder();
    }



}
