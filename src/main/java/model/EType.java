package model;

public enum EType {

    ACTION("ACTION"), CARTOON("CARTOON"), HORROR("HORROR"),COMEDY("COMEDY"),
    SPORT("SPORT"),FICTION("FICTION"),DRAMA("DRAMA");
    private String name;

    EType( String name){


        this.name = name;
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

       public static EType getTypeByName(String name) {
        for (EType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
