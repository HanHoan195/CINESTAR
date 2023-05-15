package view;

import filled.PrintColor;
import model.ERoom;
import model.Seat;
import model.ShowTime;
import service.SeatService;

import java.util.ArrayList;
import java.util.List;

public class SeatView {
//    private SeatService seatService;

//    public SeatView() {
//        seatService = new SeatService();
//    }
    SeatService seatService = new SeatService();
        public void displayRoomByShowTime(ShowTime showTime) {
        List<Seat> seats = seatService.getSeatsByIdRoom(showTime.getIdRoom());
        ERoom room = showTime.getIdRoom();
        List<Long> occupiedSeatId = new ArrayList<>();
        for (Seat seat : showTime.getOccupiedSeats()) {
            occupiedSeatId.add(seat.getId());
        }
        int row = room.getRow();
        int col = room.getCol();
        int count = 1;

        System.out.println("\t\t\t╔═════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                MÀN HÌNH");
        System.out.println();
            System.out.println();

        int[][] demoRoom = new int[row][col];
        for (int i = 0; i < demoRoom.length; i++) {
            for (int j = 0; j < demoRoom[i].length; j++) {
                demoRoom[i][j] = 0;
                for (int k = 0; k < occupiedSeatId.size(); k++) {
                    if (occupiedSeatId.get(k) == seats.get(count - 1).getId()) {
                        demoRoom[i][j] = 1;
                    }
                }
                count++;
            }
        }
        count = 0;
        for (int i = 0; i < demoRoom.length; i++) {
            for (int k = 0; k < demoRoom[i].length; k++) {
                if (demoRoom[i][k] == 1) {
                    System.out.print(PrintColor.RED + "\t\t\t\t┌─────┐" + PrintColor.RESET);
                  } else {
                    System.out.print("\t\t\t\t┌─────┐");
                  }
                }
            System.out.println();
            for (int j = 0; j < demoRoom[i].length; j++) {
                if (demoRoom[i][j] == 0) {
                    System.out.printf("\t\t\t\t│ %s  │", seats.get(count).getPosition());
                } else {
                    System.out.print(PrintColor.RED);
                    System.out.printf("\t\t\t\t│ %s  │", seats.get(count).getPosition());
                    System.out.print(PrintColor.RESET);
                }
                count++;
            }
            System.out.println();
            for (int k = 0; k < demoRoom.length; k++) {
                if (demoRoom[i][k] == 1) {
                    System.out.print(PrintColor.RED + "\t\t\t\t└─────┘" + PrintColor.RESET);
                } else {
                    System.out.print("\t\t\t\t└─────┘");
                }
            }
            System.out.println();
            }
        System.out.println(PrintColor.RED + "┌─────┐");
        System.out.print("│     │");
        System.out.println("  occupied seat         ");
        System.out.println("└─────┘");
        System.out.println(PrintColor.RESET + "┌─────┐");
        System.out.print("│     │");
        System.out.println("  empty seat         ");
        System.out.println("└─────┘");
    }

    public void displayRoom(ERoom room) {
        List<Seat> list = seatService.getSeatsByIdRoom(room);
        int row = room.getRow();
        int col = room.getCol();
        int count = 0;
        System.out.println("\t\t\t╔═════════════════════════════════════════════════════════════════════════╗");
        System.out.println("                 MÀN HÌNH");
        System.out.println();
        System.out.println();
        for (int i = 0; i < row; i++) {
            for (int k = 0; k < col; k++) {
                System.out.print("\t\t\t\t┌─────┐");
            }
            System.out.println();
            for (int j = 0; j < col; j++) {
                System.out.printf("\t\t\t\t│ %s  │", list.get(count).getPosition());
                count++;
            }
            System.out.println();
            for (int k = 0; k < col; k++) {
                System.out.print("\t\t\t\t└─────┘");
            }
            System.out.println();
        }
        System.out.println(PrintColor.RED + "┌─────┐");
        System.out.print("│     │");
        System.out.println("  occupied seat         ");
        System.out.println("└─────┘");
        System.out.println(PrintColor.RESET + "┌─────┐");
        System.out.print("│     │");
        System.out.println("  empty seat         ");
        System.out.println("└─────┘");
    }

//    public static void main(String[] args) {
//        SeatView seatView = new SeatView();
//        ShowTime showTime = new ShowTime();
//        seatView.displayRoom(ERoom.ROOM_2);
//    }
}
