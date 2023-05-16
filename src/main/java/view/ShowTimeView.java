package view;

import model.EFormat;
import model.ERoom;
import model.Film;
import model.ShowTime;
import repository.FileService;
import service.FilmService;
import service.ShowTimeService;
import utils.DateUtils;
import utils.ValidateShowTime;
import utils.ValidateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


import static view.Menu.manageShowtime;
import static view.Menu.menuManageFilm;

public class ShowTimeView {
    private final String filePath = "D:\\CASE_MD2\\CINESTAR\\src\\main\\java\\data\\showtime.csv";

    ShowTimeService showTimeService = new ShowTimeService();
    //        filmView = new FilmView();
    ValidateShowTime validateShowTime = new ValidateShowTime();
    FilmService filmService = new FilmService();
    FileService fileService = new FileService();

//    }


    public ShowTimeView() {
    }

    public void displayAllShowTimes() { //hiển thị phim và thời gian chiếu phim
        List<ShowTime> list = showTimeService.getAllShowTimes();
        System.out.println("                        ╔═══════╔═══════╦══════════════════════════════╦═════════════════════╦═════════════════════╦════════╦════════╗");
        System.out.printf("                        ║  %-4s ║%-4s ║ %-29s║ %-20s║ %-20s║ %-7s║ %-7s║", "ID", "IDFilm", "Film Name", "Start Time", "End Time", "Room", "Format");
        System.out.println();
        System.out.println("                        ╠═══════╠═══════╬══════════════════════════════╬═════════════════════╬═════════════════════╬════════╬════════╣");
        for (int i = 0; i < list.size(); i++) {
            if (i == (list.size() - 1)) {
                System.out.println(list.get(i).toView());
                System.out.println("                        ╚═══════╚═══════╩══════════════════════════════╩═════════════════════╩═════════════════════╩════════╩════════╝");
            } else {
                System.out.println(list.get(i).toView());
                System.out.println("                        ╠═══════╠═══════╬══════════════════════════════╬═════════════════════╬═════════════════════╬════════╬════════╣");
            }
        }
        // checkActionContinue();
    }


    public void displayShowTime(ShowTime showTime) {//hiển thị tgian chiếu
        System.out.println("                        ╔═══════╔═══════╦══════════════════════════════╦═════════════════════╦═════════════════════╦════════╦════════╗");
        System.out.printf("                        ║  %-4s ║%-4s ║ %-29s║ %-20s║ %-20s║ %-7s║ %-7s║", "ID", "IDFilm", "Film Name", "Start Time", "End Time", "Room", "Format");
        System.out.println();
        System.out.println("                        ╠═══════╠═══════╬══════════════════════════════╬═════════════════════╬═════════════════════╬════════╬════════╣");
        System.out.println(showTime.toView());
        System.out.println("                        ╚═══════╚═══════╩══════════════════════════════╩═════════════════════╩═════════════════════╩════════╩════════╝");
    }

    public void renderRoom() {//hiển thị chọn phòng
        System.out.println("                                                ┌────────────┬────────────┐");
        System.out.printf("                                                |   %-9s|   %-9s|", "ID", "ROOM");
        System.out.println();
        System.out.println("                                                ├────────────┼────────────┤");
        ERoom[] rooms = ERoom.values();
        for (int i = 0; i < rooms.length; i++) {
            if (i == (rooms.length - 1)) {
                System.out.printf("                                                |   %-9s|   %-9s|", rooms[i].getId(), rooms[i].getName());
                System.out.println();
                System.out.println("                                                └────────────┴────────────┘");
            } else {
                System.out.printf("                                                |   %-9s|   %-9s|", rooms[i].getId(), rooms[i].getName());
                System.out.println();
                System.out.println("                                                ├------------┼------------┤");
            }
        }
    }

    public void renderFormatFilm() {//hiển thị format phim
        System.out.println("                                                ┌─────────────┬─────────────┐");
        System.out.printf("                                                |   %-10s|   %-10s|", "ID", "Format");
        System.out.println();
        System.out.println("                                                ├─────────────┼─────────────┤");
        EFormat[] formats = EFormat.values();
        for (int i = 0; i < formats.length; i++) {
            if (i == (formats.length - 1)) {
                System.out.printf("                                                |   %-10s|   %-10s|", formats[i].getId(), formats[i].getNameFormat());
                System.out.println();
                System.out.println("                                                └─────────────┴─────────────┘");
            } else {
                System.out.printf("                                                |   %-10s|   %-10s|", formats[i].getId(), formats[i].getNameFormat());
                System.out.println();
                System.out.println("                                                ├-------------┼-------------┤");
            }
        }
    }

    public boolean checkBeforeSave(ShowTime showTime) {//kiểm tra trước khi lưu
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vui lòng kiểm tra lại thông tin trước khi lưu!");
        displayShowTime(showTime);
        System.out.println("Bạn cóm muốn lưu không? Y/N");
        System.out.print(" \t☛ ");
        String choice = scanner.nextLine().toUpperCase();
        boolean save;
        switch (choice) {
            case "Y":
                save = true;
                break;
            case "N":
                save = false;

                System.out.println("Cập nhật lại nhấn \"1\"");
                System.out.println("Quay lại trang chủ nhấn \"2\"");
                System.out.print("\t➥ ");
                int choose = Integer.parseInt(scanner.nextLine());
                if (choose == 1) {
                    addNewShowTime();
                    break;
                } else {
                    menu.manager();
                    // showTimeService.deleteById(showTime.getId());
                    break;
                }

            default:
                System.out.println("Giá trị không hợp lệ. Đã lưu thông tin của bạn");
                save = false;
        }
        return save;

    }

    public boolean checkActionContinueEdit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bạn muốn tiếp tục chỉnh sửa không? Y/N");
        System.out.print(" \t☛ ");
        String choice = scanner.nextLine().toUpperCase();
        switch (choice) {
            case "Y":
                return true;
            case "N":
                return false;
            default:
                System.out.println("Không đúng định dạng! Vui lòng nhập lại!");
                return true;
        }
    }

    public void editShowTime(ShowTime showTime) {
        Scanner scanner = new Scanner(System.in);
        boolean continueEdit = true;
        List<ShowTime> showTimes;
        //do {

        //cập nhật thời gian chiếu(ok)
        displayAllShowTimes();
        boolean checkAction1 = true;
        long idFilm = 0;
        showTimes = showTimeService.getAllShowTimes();

        do {
            try {
                System.out.println("Vui lòng nhập IDShowtime cần cập nhật: ");
                System.out.print(" \t☛ ");
                idFilm = Long.parseLong(scanner.nextLine());
                if (idFilm <= 0) {
                    throw new NumberFormatException("ID phải lớn hơn hoặc bằng 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Id phải là một số lớn hơn 0!");
            }
            boolean existId = false;
            for (ShowTime showTime1 : showTimes) {
                if (showTime1.getId() == idFilm) {
                    //showTime.setIdFilm(idFilm);
                    existId = true;
                    String start;
                    System.out.println("Nhập thời gian theo định dạng: 'yyyy-MM-dd HH:mm'");
                    System.out.println("VD: 2023-01-01 00:00");
                    System.out.print(" \t☛ ");
                    do {

                        start = scanner.nextLine();
                        if (checkShowTimeBeforeNow(showTime)) {
                            System.out.println("Không thể thêm suất chiếu vì thời gian bắt đầu trước thời điểm hiện tại!");
                        }
                        if (validateShowTime.checkEditShowTime(start, showTime1)) {
                            System.out.println("Không đúng định dạng hoặc đã trùng suất chiếu!");
                        }
                    } while (validateShowTime.checkEditShowTime(start, showTime1));

                    Date startTime = DateUtils.parseDate(start);
                    showTime1.setStartTime(startTime);

                    long durationTime = filmService.findDurationTimeById(showTime1.getIdFilm());
                    showTime1.setEndTime(DateUtils.plusTime(startTime, durationTime));

                    checkBeforeSave(showTime1);
                    break;
                }
            }
            if (!existId) {
                System.out.println("Không tìm thấy suất chiếu với ID : " + idFilm);
            }

        } while (!checkAction1);


        // } while (continueEdit);

        fileService.writeData(filePath, showTimes);

    }


    public ShowTime findShowTimeById(long id) {
        ShowTime[] listShowTimes = new ShowTime[0];
        for (ShowTime showTime : listShowTimes) {
            if (showTime.getId() == id) {
                return showTime;
            }
        }
        return null;
    }


    public void addNewShowTime() {//thêm tgian chiếu mới
        Scanner scanner = new Scanner(System.in);
        long id = System.currentTimeMillis() % 1000;
               displayAllShowTimes();
        //long id, long idFilm, Date startTime, ERoom idRoom, EType type
        ShowTime showtime = new ShowTime();
        long idFilm = 0;
        boolean checkID = false;

        do {
            System.out.println("chọn ID phim:");
            System.out.print(" \t☛ ");
            try {
                idFilm = Long.parseLong(scanner.nextLine());
                if (idFilm > 0) {
                    if (filmService.checkIdFilm(idFilm)) {
                        showtime.setId(id);
                        showtime.setIdFilm(idFilm);
                    } else {
                        System.out.println("ID phim không tồn tại!");
                        addNewShowTime();
                    }
                } else {
                    addNewShowTime();
                }
            } catch (NumberFormatException e) {
                System.out.println("Không đúng.Vui lòng nhập lại!");
                addNewShowTime();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (checkID);



        //start time, room


        String start;
        boolean checkStartTime = false;
        Date startTime;
        ERoom room;
        do {
            System.out.println("Nhập thời gian chiếu theo format : 'year-month-day hour:minute')");
            System.out.println("VD: 2023-05-05 19:05");
            System.out.print(" \t☛ ");
            start = scanner.nextLine();

            checkStartTime = ValidateUtils.isShowTime(start);
            if (!checkStartTime) {
                System.out.println("Không hợp lệ. Vui lòng nhập lại!");
                addNewShowTime();
            }


            startTime = DateUtils.parseDate(start);
            showtime.setStartTime(startTime);
            checkStartTime = checkShowTimeBeforeNow(showtime);
            if (checkStartTime) {
                System.out.println("Không thể thêm suất chiếu vì thời gian bắt đầu trước thời điểm hiện tại!");
                continue;
            }
        } while (checkStartTime );


            renderRoom();
        boolean checkIDRoom;
        do {
            System.out.println("Nhập ID phòng:");
            System.out.print(" \t☛ ");
            try {
                long idRoom = Long.parseLong(scanner.nextLine());
                if (idRoom > 0) {
                    room = ERoom.toERoom(idRoom);
                    showtime.setIdRoom(room);
                    showtime.setEndTime(DateUtils.plusTime(startTime, filmService.findDurationTimeById(idFilm)));
                    showtime.setQuantitySeat(room.getAmountSeat());
                    showtime.setEmptySeat(room.getAmountSeat());
                    checkIDRoom = validateShowTime.checkNewValidateShowTime(start, showtime);
                    if (checkIDRoom) {
                        System.out.println("Không đúng. Vui lòng nhập lại!");
                    }
                } else {
                    System.out.println("Không đúng. Vui lòng nhập lại!");
                    checkIDRoom = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Không đúng. Vui lòng nhập lại!");
                checkIDRoom = true;
            }
        } while (checkIDRoom);






        //format
        boolean checkFormat = false;
        renderFormatFilm();

        do {
            try {
                System.out.println("Nhập ID định dạng:");
                System.out.print(" \t☛ ");
                long idFormat = Long.parseLong(scanner.nextLine());
                if (idFormat >= 0) {
                    EFormat format = EFormat.toFormat(idFormat);
                    showtime.setFormat(format);

                    checkFormat = true; // Đặt lại biến kiểm tra thành true để thoát khỏi vòng lặp
                } else {
                    System.out.println("ID định dạng không hợp lệ. Vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Không đúng. Vui lòng nhập lại!");
            }
        } while (!checkFormat);



        checkBeforeSave(showtime);
        showTimeService.add(showtime);
    }




    public boolean checkActionContinue() {
        Scanner scanner = new Scanner(System.in);
        boolean checkActionContinue = false;
        do {
            System.out.println("Continue? Y/N");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    checkActionContinue = true;
            }
        } while (checkActionContinue);
        return false;
    }

    public List<ShowTime> searchByName(String name) {
        return showTimeService.searchByName(name);
    }


    public void deleteShowTime() {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        displayAllShowTimes();
        System.out.println("Nhập ID Showtime bạn muốn xóa hoặc nhập '0' để quay lại menu: ");
        System.out.print("\t➥ ");
        long idShowtime = 0;

        try {
            idShowtime = Long.parseLong(scanner.nextLine());
            switch ((int) idShowtime) {
                case 0:
                manageShowtime();
                break;
            }

        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ Vui lòng nhập lại!");
            deleteShowTime();
            return;
        }


        // Kiểm tra xem ID có tồn tại không
        ShowTime showtime = showTimeService.findById(idShowtime);
        if (showtime == null) {
            System.out.println("Không tìm thấy suất chiếu với ID : " + idShowtime);
            deleteShowTime();
            return;
        }

        // Hiển thị thông báo xác nhận việc xóa

        if (confirmDelete()) {
            showTimeService.deleteById(idShowtime);
            System.out.println("Đã xóa suất chiếu có ID: " + idShowtime);
            displayAllShowTimes();
        }
        //checkActionContinue();
        //menu.menuManager();
    }

    public static boolean confirmDelete() {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.println("Bạn có chắc chắn muốn xóa suất chiếu này không? (Y/N)");
            System.out.print("\t➥ ");
            input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                menu.manageShowtime();
                return false;
            } else {
                System.out.println("Vui lòng nhập Y hoặc N");
            }
        }
    }


    public boolean checkShowTimeBeforeNow(ShowTime showTime) {
        Date now = new Date();
        if (showTime.getStartTime().before(now)) {
            return true;
        }
        return false;
    }


    public Film findFilmById(int id) {
        // Assuming you have a list of films stored somewhere, such as in a database
        Film[] filmList = new Film[0];
        for (Film film : filmList) {
            if (film.getId() == id) {
                return film;
            }
        }
        return null; // If no film with the given id is found
    }


    public void noChange() {
        System.out.println(" ⦿ Nếu hủy thao tác, quay lại menu thì nhập: \"0\" ⦿ ");
    }



}
