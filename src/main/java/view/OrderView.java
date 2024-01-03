package view;

import model.*;
import repository.ShowTimeRepository;
import service.*;
import utils.CurrencyFormat;
import utils.DateUtils;
import utils.ValidateUtils;

import java.util.*;

public class OrderView {

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
        Ticket ticket;
        do {
            ticket = addNewTicket();
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
//            displayOrderDetail(idOrder);//hóa đơn
            displayOrderDetail(idOrder, ticketList);

            checkContinueAdd();
            Menu menu = new Menu();
            menu.customer();


        } else {
            System.out.println("Đơn đặt hàng của bạn trống!!!");
            orderService.deleteById(idOrder);
        }

    }

    public void displayOrderDetail(long idOrder, List<Ticket> tickets) {
        Order order = orderService.getOrderByIdOrder(idOrder);
        if (order == null) {
            System.out.println("Không tìm thấy đơn đặt hàng này");
            return;
        }
//        List<Ticket> tickets = order.getOrderitemList();
        System.out.println("                                  ╔═══════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "ID Order", order.getId()).println();
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "Customer's name", order.getCustomerName()).println();
        System.out.printf("                                  ║%-10s %-20s %-59s║", " ", "Create Date", DateUtils.convertDateToString(order.getCreateDate())).println();
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");
        System.out.printf("                                  ║%7s|%-30s|%-20s|%-7s|%-7s|%-15s║", "ID", "Film", "Start", "Room", "Seat", "Price").println();
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");

        double totalPrice = 0.0d;
        for (Ticket ticket : tickets) {
            System.out.println(ticket.simpleView());
            totalPrice += ticket.getTotalPrice();
        }
        System.out.println("                                  ║-------------------------------------------------------------------------------------------║");
        System.out.printf("                                  ║%20s %53s %-15s ║", "Total", " ", CurrencyFormat.covertPriceToString(totalPrice)).println();
        System.out.printf("                                  ║%91s║", " ").println();
        System.out.printf("                                  ║%20s %39s %-30s║", " ", "THANKS FOR SHOPPING!!!", " ").println();
        System.out.println("                                  ╚═══════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public long checkExistCustomer() {

        while (true) {
            try {
                System.out.println("Nhập tên khách hàng:");
                System.out.print("\t➥ ");
                String customerName = scanner.nextLine();
                List<User> result = customerService.searchByName(customerName);
                if (result.size() != 0) {
                    customerView.displayAllCustomer(result);
                    while (true) {
                        System.out.println("Chọn ID khách hàng:");
                        System.out.print("\t➥ ");
                        long idCustomer;
                        try {
                            idCustomer = Long.parseLong(scanner.nextLine());
                            boolean found = false;
                            for (User customer : result) {
                                if (customer.getId() == idCustomer) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                return idCustomer;
                            } else {
                                System.out.println("ID không hợp lệ. Vui lòng nhập lại.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("ID không hợp lệ. Vui lòng nhập lại.");
                        }
                    }
                } else {
                    System.out.println("Không tìm thấy khách hàng nào. Vui lòng nhập lại.");
                }
            } catch (Exception e) {
                System.out.println("Có lỗi xảy ra khi nhập tên khách hàng: " + e.getMessage());
            }
        }


    }
    public boolean checkContinueAdd() {
        boolean checkContinue = false;
        do {
            System.out.println("Bạn muốn tiếp tục không? Y/N");
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
        Menu menu = new Menu();
        ShowTimeView showTimeView = new ShowTimeView();
        Ticket ticket = new Ticket();
        long id = System.currentTimeMillis() % 1000;
        showTimeView.displayAllShowTimes();
        long idShowTime = 0;
        boolean emptySeat = false;
        int count = 0;

        do {
            try {
                System.out.println("Chọn ID suất chiếu bạn muốn mua vé hoặc nhập '0' để quay về menu:");
                System.out.print("\t➥ ");
                idShowTime = Long.parseLong(scanner.nextLine());

                if (idShowTime == 0) {
                    menu.customer();
                }

                if (idShowTime < 0) {
                    throw new InputMismatchException();
                }

                boolean overdue = checkShowTimeOverdue(idShowTime);
                if (overdue == true) {
                    System.out.println("Suất chiếu đã quá hạn!.");
                    return null;
                }

                emptySeat = showTimeService.checkEmptySeat(idShowTime);
                if (!emptySeat) {
                    System.out.println("Suất chiếu này không còn ghế trống vui lòng chọn suất chiếu khác!");
                }

                count++;

                if (count == countEnter) {
                    boolean checkContinue = checkContinueAddTicket();
                    if (!checkContinue) {
                        return null;
                    }
                }

            } catch (Exception e) {
                System.out.println("Không đúng vui lòng nhập lại!");
                continue;
            }

        } while (!emptySeat);

        String seatInput;
        count = 0;
        long idSeat;

        do {
            if (showTimeService.findById(idShowTime).getOccupiedSeats() == null) {
                seatView.displayRoom(showTimeService.findById(idShowTime).getIdRoom());
            } else {
                seatView.displayRoomByShowTime(showTimeService.findById(idShowTime));
            }
            ShowTime showTime = showTimeService.findById(idShowTime);
            List<Seat> seats = seatService.getSeatsByIdRoom(showTime.getIdRoom());


            int checkSeat;
            do {
                System.out.println("Vui lòng chọn ghế hoặc nhập '0' để quay về menu: ");
                System.out.print("\t➥ ");

                seatInput = scanner.nextLine().toUpperCase();
                if (seatInput.equals("0") ) {
                    menu.customer();
                }

                checkSeat = 0;
                for (Seat seat : seats) {
                    if (seatInput.equalsIgnoreCase(seat.getPosition())) {
                        checkSeat = 1;
                    }
                }
                if (checkSeat == 0) {
                    continue;
                }
            } while (checkSeat == 0);


            if (!checkOccupiedSeat(idShowTime, seatInput)) {
                System.out.println("Ghế đã được chọn!Vui lòng chọn lại!");
            }

            count++;

            if (count == countEnter) {
                boolean checkContinue = checkContinueAddTicket();
                if (!checkContinue) {
                    return null;
                }
            }

        } while (!checkOccupiedSeat(idShowTime, seatInput));

        System.out.println("Nhập '0' để hủy vé hoặc nhấn phím bất kỳ để xác nhận việc chọn vé!");
        System.out.print("\t➥ ");
        String input = scanner.nextLine();

        if (input.equals("0")) {
            System.out.println("Đã hủy vé thành công.");
            return null;
        } else {
            System.out.println("Bạn đã chọn vé thành công!");
        }




        ERoom room = showTimeService.findById(idShowTime).getIdRoom();
        idSeat = seatService.getIdSeatByRoom(seatInput, showTimeService.findById(idShowTime));



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





    public void noChange() {
        System.out.println(" Quay lại Menu thì nhập: \"0\" ");
    }


    public void showRevenueByDay() {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> orderAll = orderService.getAllOrder();
        if (orderAll.isEmpty()) {
            System.out.println("Hiện tại doanh thu đang trống!");
        } else {
            String date = null;
            boolean checkDate = false;
            do {
                System.out.println("Nhập \"năm-tháng-ngày\" bạn muốn xem doanh thu: ");
                System.out.println("VD: 2023-05-05");
                System.out.print("\t➥ ");
                date = scanner.nextLine();
                if (date.equals("0")) {
                    checkDate = true;
                    adminView.launcherRevenue();
                }
                checkDate = ValidateUtils.isDay(date);
                if (!checkDate) {
                    System.out.println("Không hợp lệ. Vui lòng nhập lại!");
                }
            } while (!checkDate);

            int count = 0;
            for (int i = 0; i < orderAll.size(); i++) {
                if (DateUtils.convertDateToString(orderAll.get(i).getCreateDate()).contains(date)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.printf("Ngày %s không có doanh thu!", date);
            } else {
                double totalRevenueByDay = 0;
                System.out.println("            ╔═══════════════════════════════╦═════════════════════╦════════════════╗");
                System.out.printf("            ║ %-30s║ %19s ║ %14s ║", "FILM NAME",  " DATE ODER",  "TOTAL").println();
                System.out.println("            ╠═══════════════════════════════╬═════════════════════╬════════════════╣");
                FilmService filmService = new FilmService();

                for (int i = 0; i < orderAll.size(); i++) {
                    if (DateUtils.convertDateToString(orderAll.get(i).getCreateDate()).contains(date)) {
                        totalRevenueByDay += orderAll.get(i).getTotal();

                        System.out.printf(orderAll.get(i).oderView(orderAll.get(i).getId())).println();
                    }
                }
                System.out.println("            ╠═══════════════════════════════╣═════════════════════╩════════════════╣");
                System.out.printf("            ║     TỔNG DOANH THU            ║       %30s ║    ", CurrencyFormat.covertPriceToString(totalRevenueByDay)).println();
                System.out.println("            ╚═══════════════════════════════╩══════════════════════════════════════╝");
            }
        }
    }




    public void showRevenueByMonth() {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> ordersAll = orderService.getAllOrder();
        if (ordersAll.isEmpty()) {
            System.out.println("Danh sách hiện đang trống!!");
        } else {
            String month = null;
            boolean checkMonth = false;
            do {
                System.out.println("Nhập \"năm-tháng\" bạn muốn xem doanh thu:");
                System.out.println("VD: 2023-05");
                System.out.print("\t➥ ");
                month = scanner.nextLine();
                if (month.equals("0")) {
                    checkMonth = true;
                    adminView.launcherRevenue();
                }
                checkMonth = ValidateUtils.isMonth(month);
                if (!checkMonth) {
                    System.out.println("Không hợp lệ.Vui lòng nhập lại!");
                }
            } while (!checkMonth);

            int count = 0;
            for (int i = 0; i < ordersAll.size(); i++) {
                if (DateUtils.convertDateToString(ordersAll.get(i).getCreateDate()).contains(month)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Danh sách hiện đang trống!");
            } else {
                double totalRevenueByMonth = 0;
                System.out.println("            ╔═══════════════════════════════╦═════════════════════╦════════════════╗");
                System.out.printf("            ║ %-30s║ %19s ║ %14s ║",   "FILM NAME", " DATE ODER", "TOTAL").println();
                System.out.println("            ╠═══════════════════════════════╬═════════════════════╬════════════════╣");
                for (int i = 0; i < ordersAll.size(); i++) {
                    if (DateUtils.convertDateToString(ordersAll.get(i).getCreateDate()).contains(month)) {
                        totalRevenueByMonth += ordersAll.get(i).getTotal();
                        System.out.printf(ordersAll.get(i).oderView(ordersAll.get(i).getId())).println();
                    }
                }
                System.out.println("            ╠═══════════════════════════════╣═════════════════════╩════════════════╣");
                System.out.printf("            ║     TỔNG DOANH THU            ║       %30s ║                                                ", CurrencyFormat.covertPriceToString(totalRevenueByMonth)).println();
                System.out.println("            ╚═══════════════════════════════╩══════════════════════════════════════╝");
                System.out.println();
            }
        }
    }




    public void showRevenueByYear() {
        AdminView adminView = new AdminView();
        noChange();
        List<Order> ordersAll = orderService.getAllOrder();
        if (ordersAll.isEmpty()) {
            System.out.println("Danh sách hiện đang trống!!");
        } else {
            String year = null;
            boolean checkyear = false;
            do {
                System.out.println("Nhập \"năm\" bạn muốn xem doanh thu:");
                System.out.println("VD: 2023");
                System.out.print("\t➥ ");
                year = scanner.nextLine();
                if (year.equals("0")) {
                    checkyear = true;
                    adminView.launcherRevenue();
                }
                checkyear = ValidateUtils.isYear(year);
                if (!checkyear) {
                    System.out.println("Không hợp lệ.Vui lòng nhập lại!");
                }
            } while (!checkyear);

            int count = 0;
            for (int i = 0; i < ordersAll.size(); i++) {
                if (DateUtils.convertDateToString(ordersAll.get(i).getCreateDate()).contains(year)) {
                    count += 1;
                }
            }
            if (count == 0) {
                System.out.println("Danh sách hiện đang trống!");
            } else {
                double totalRevenueByMonth = 0;
                System.out.println("            ╔═══════════════════════════════╦═════════════════════╦════════════════╗");
                System.out.printf("            ║ %-30s║ %19s ║ %14s ║",   "FILM NAME", " DATE ODER", "TOTAL").println();
                System.out.println("            ╠═══════════════════════════════╬═════════════════════╬════════════════╣");
                for (int i = 0; i < ordersAll.size(); i++) {
                    Order order = ordersAll.get(i);

                    if (DateUtils.convertDateToString(ordersAll.get(i).getCreateDate()).contains(year)) {

                        totalRevenueByMonth += ordersAll.get(i).getTotal();

                        System.out.printf(ordersAll.get(i).oderView(ordersAll.get(i).getId())).println();
                    }
                }
                System.out.println("            ╠═══════════════════════════════╣═════════════════════╩════════════════╣");
                System.out.printf("            ║     TỔNG DOANH THU            ║       %30s ║                                                ", CurrencyFormat.covertPriceToString(totalRevenueByMonth)).println();
                System.out.println("            ╚═══════════════════════════════╩══════════════════════════════════════╝");
                System.out.println();
            }
        }
    }
}
