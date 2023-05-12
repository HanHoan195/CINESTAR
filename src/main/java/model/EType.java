package model;

public enum EType {

    ACTION(1,"ACTION"), CARTOON(2,"CARTOON"), HORROR(3,"HORROR"),COMEDY(4,"COMEDY"),
    SPORT(5,"SPORT"),FICTION(6,"FICTION"),DRAMA(7,"DRAMA");
    private String name;
    private long id;
    EType(long id, String name){

        this.id= id;
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

    public static EType getType(String name){
        for (EType eType : values()) {
            if (eType.getName().equals(name)) {
                return eType;
            }
        }
       return null;
    }

    public static EType toType(long id){
        for (EType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return null;
    }
    public static EType getTypeByName(String name) {
        for (EType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
