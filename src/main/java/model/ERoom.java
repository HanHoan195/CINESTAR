package model;

public enum ERoom {
    ROOM_1(1, 4, 4, "ROOM_1"),
    ROOM_2(2, 3, 3, "ROOM_2");
    private long id;
    private int row;
    private int col;
    private String name;

    ERoom(long id, int row, int col, String name) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public int getAmountSeat() {
        return this.col * this.row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ERoom toERoom(long id) {
        for (ERoom room : values()) {
            if (room.id == id) {
                return room;
            }
        }
        return null;
    }
    public static ERoom getERoomByName(String name) {
        for (ERoom room : values()) {
            if (room.toString().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
