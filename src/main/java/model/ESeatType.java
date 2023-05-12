package model;

import java.util.PrimitiveIterator;

public enum ESeatType {
    SINGLE_SEAT(1,"SINGLE_SEAT",0), COUPLE_SEAT(2,"COUPLE_SEAT",45000);
    private long id;
    private String name;
    private double surcharge;

    ESeatType(long id, String name, double surcharge) {
        this.id = id;
        this.name= name;
        this.surcharge = surcharge;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }
}
