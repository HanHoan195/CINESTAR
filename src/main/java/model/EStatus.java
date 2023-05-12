package model;

public enum EStatus {
    NOW_SHOWING(1, "NOW_SHOWING"),
    COMING_SOON(2, "COMING_SOON");
    private long id;
    private String name;


    EStatus(long id, String name){
        this.id = id;
        this.name = name;
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

    public static EStatus toStatus(long id) {
        for (EStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }
        return null;
    }
    public static EStatus getStatusByName(String name) {
        for (EStatus status : values()) {
            if (status.getName().equals(name)) {
                return status;
            }
        }
        return null;
    }
}
