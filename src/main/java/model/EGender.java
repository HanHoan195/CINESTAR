package model;

public enum EGender {
    MALE("MALE"),
    FEMALE( "FEMALE"),
    OTHER( "OTHER");


    private String name;

    EGender( String name) {

        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static EGender getEGenderByName(String name) {
        for (EGender gender : values()) {
            if (gender.getName().equals(name)) {
                return gender;
            }
        }
        return null;
    }
}
