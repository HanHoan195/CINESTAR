package model;

public enum EFormat {
    _2D(1, 0, "2D"),
    _3D(2, 20000, "3D");

    private long id;
    private double surcharge;
    private String nameFormat;
    EFormat(long id, double surcharge, String nameFormat) {
        this.id = id;
        this.surcharge = surcharge;
        this.nameFormat = nameFormat;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(double surcharge) {
        this.surcharge = surcharge;
    }

    public String getNameFormat() {
        return nameFormat;
    }

    public void setNameFormat(String nameFormat) {
        this.nameFormat = nameFormat;
    }

    public static EFormat toFormat(long id) {
        for (EFormat format : values()) {
            if (format.id == id) {
                return format;
            }
        }
        return null;
    }
    public static EFormat getFormatByName(String nameFormat) {
        for (EFormat format : values()) {
            if (format.getNameFormat().equals(nameFormat)) {
                return format;
            }
        }
        return null;
    }
}
