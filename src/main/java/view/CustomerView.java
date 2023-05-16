package view;

import model.*;
import service.CustomerService;
import service.SeatService;
import service.ShowTimeService;
import service.TicketService;
import utils.ValidateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static view.Menu.menuCustomer;


public class CustomerView {
    TicketService ticketService = new TicketService();
    SeatService seatService = new SeatService();
    ShowTimeView showTimeView ;
    SeatView seatView = new SeatView();
    Ticket ticket = new Ticket();
    CustomerService customerService = new CustomerService();
    Scanner scanner = new Scanner(System.in);




    public void bookTicket() {
        showTimeView.displayAllShowTimes();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Nhập ID suất chiếu: ");
         long id = Long.parseLong(scanner.nextLine());

        ShowTimeService showTimeService = new ShowTimeService();
        ShowTime showTime = showTimeService.getShowtimeById(id);
        if (showTime == null) {
            String showTimeId = null;
            System.out.println("Không tìm thấy suất chiếu với ID: " + showTimeId);
            return;
        }


        List<Seat> seat = seatService.getSeatsByIdRoom(showTime.getIdRoom());
        seatView.displayRoomByShowTime(showTime);
        System.out.println("Chon ghế:");
        long seatId = Long.parseLong(scanner.nextLine());
        Seat selectedSeat = seatService.getSeatById(seatId);

        if (selectedSeat == null) {
            System.out.println("Không tìm thấy ghế với ID: " + seatId);
            return;
        }

        // Yêu cầu người dùng nhập thông tin
        System.out.println("Vui lòng nhập thông tin của bạn để đặt vé: ");
        System.out.print("Tên: ");
        String name = scanner.nextLine();

        System.out.print("Số điện thoại: ");
        String phone = scanner.nextLine();

// Tạo vé mới và lưu vào cơ sở dữ liệu
        Ticket ticket = new Ticket(showTime.getId(), selectedSeat.getId(), name, phone);
        ticketService.addNewTicket(ticket);

        System.out.println("Đặt vé thành công!");

    }

    private boolean confirmBooking() {
        System.out.println("Bạn chắc chắn muốn đặt vé? (Y/N)");
        System.out.print("\t➥ ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String answer = scanner.nextLine().toUpperCase();
            if (answer.equalsIgnoreCase("Y")) {
                return true;
            } else if (answer.equalsIgnoreCase("N")) {
                return false;

            }
        }
    }

    public void addNewCustomer() {
        Menu menu = new Menu();
        User user = new User();
        long id = System.currentTimeMillis() % 1000;

        System.out.println("Nhập tên khách hàng:");
        System.out.print("\t➥ ");
        String name = scanner.nextLine().toUpperCase();

        int age;
        while (true) {
            System.out.println("Nhập tuổi khách hàng (>= 12):");
            System.out.print("\t➥ ");
            try {
                age = Integer.parseInt(scanner.nextLine());
                if (age < 12) {
                    System.out.println("Bạn chưa đủ tuổi để đặt vé xem phim!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Tuổi không hợp lệ! Vui lòng nhập một số.");
            }
        }
        boolean loop = false;
        int gender;


        do {
            System.out.println("Chọn giới tính:");
            System.out.println("1. Male");
            System.out.println("2. Female");
            System.out.println("3. Other");
            System.out.print("\t➥ ");

            try {
                gender = Integer.parseInt(scanner.nextLine());
                switch (gender){
                    case 1:
                        user.setGender(EGender.MALE);
                        break;
                    case 2:
                        user.setGender(EGender.FEMALE);
                        break;
                    case 3:
                        user.setGender(EGender.OTHER);
                        break;
                    default:
                        System.out.println("VUi lòng nhập lại");
                        loop = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Không đúng. Vui lòng nhập lại!");
                loop = true;
            }


        } while (loop == true);


        System.out.println("Nhập địa chỉ:");
        System.out.print("\t➥ ");
        String address = scanner.nextLine();


        String phone = checkExistPhoneNumber();


        Role role = Role.CUSTOMER;

        user.setId(id);
        user.setName(name);
        user.setAge(age);
//        user.setGender(eGender);
        user.setAddress(address);
        user.setCreate(new Date());
        user.setRole(role);
        user.setPhoneNumber(phone);

        System.out.println("Vui lòng kiểm tra lại thông tin!");
        displayUser(user);

        while (true) {
            System.out.println("Bạn có muốn lưu thông tin khách hàng này không? Y/N");
            System.out.print("\t➥ ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("Y")) {
                customerService.addNewCustomer(user);
                checkBookTicket();
                break;
            } else if (choice.equals("N")) {
                System.out.println("Bạn có muốn nhập lại thông tin không? Y/N");
                System.out.print("\t➥ ");
                String edit = scanner.nextLine().toUpperCase();
                if (edit.equals("Y")) {
                    addNewCustomer();
                } else {
                    menu.customer();
                    break;
                }

            }
        }



    }



    public void checkBookTicket() {
        System.out.println("Bạn muốn đặt vé xem phim không? Y/N");
        System.out.print("\t➥ ");
        String choice = scanner.nextLine().toUpperCase();
        switch (choice) {
            case "Y":
                OrderView orderView = new OrderView();
                orderView.addNewTicket();
                break;
            case "N":
                menuCustomer();
                break;
            default:
                System.out.println("Chọn chức năng không đúng vui lòng nhập lại!");
        }
    }

    public String checkExistPhoneNumber() {
        String number;
        boolean checkNumber;
        do {
            checkNumber = true;
            System.out.println("Nhập SĐT: ");
            System.out.println("SĐT bắt đầu bằng \"0\" và có 10 số!");
            System.out.print("\t➥ ");
            number = scanner.nextLine();
            if (!ValidateUtils.isPhoneNumber(number)) {
                System.out.println("SĐT không đúng định dạng. Vui lòng nhập lại!");
                checkNumber = false;
            }
            if (customerService.checkExistPhoneNumber(number)) {
                System.out.println("Số điện thoại đã tồn tại. Vui lòng nhập lại");
                checkNumber = false;
            }
        } while (!checkNumber);
        return number;
    }


    public void displayUser(User user) {
        System.out.println("            ╔═══════╦══════════════════════════════╦════════╦════════════════╦═════════════════════╦═════════════════════╦═════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-7s║ %-15s║ %-20s║ %-20s║ %-20s║", "ID", "Customer's name", "Age", "Gender", "Address", "Phone", "Create Date").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬════════╬════════════════╬═════════════════════╬═════════════════════╬═════════════════════╣");
        System.out.println(user.viewUser());
        System.out.println("            ╚═══════╩══════════════════════════════╩════════╩════════════════╩═════════════════════╩═════════════════════╩═════════════════════╝");
    }



    public void displayAllCustomer(List<User> customerList) {
        System.out.println("            ╔═══════╦══════════════════════════════╦════════╦════════════════╦═════════════════════╦═════════════════════╦═════════════════════╗");
        System.out.printf("            ║%7s║%-30s║ %-7s║ %-15s║ %-20s║ %-20s║ %-20s║", "ID", "Customer's name", "Age", "Gender", "Address", "Phone", "Create Date").println();
        System.out.println("            ╠═══════╬══════════════════════════════╬════════╬════════════════╬═════════════════════╬═════════════════════╬═════════════════════╣");
        for (int i = 0; i < customerList.size(); i++) {
            if (i == (customerList.size() - 1)) {
                System.out.println(customerList.get(i).viewUser());
                System.out.println("            ╚═══════╩══════════════════════════════╩════════╩════════════════╩═════════════════════╩═════════════════════╩═════════════════════╝");
            } else {
                System.out.println(customerList.get(i).viewUser());
                System.out.println("            ╠═══════╬══════════════════════════════╬════════╬════════════════╬═════════════════════╬═════════════════════╬═════════════════════╣");
            }
        }
    }


}
