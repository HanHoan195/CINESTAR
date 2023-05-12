package model;

public enum Role {
    ADMIN("ADMIN"),CUSTOMER("CUSTOMER");
    private String value;

    Role(String value) {
        this.value = value;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public static Role fromValue(String value){
        Role[] values = values();
        for (Role role:values) {
            if (role.value.equals(value))
                return role;
        }
        throw new IllegalArgumentException("invalid");
    }


        public static Role getRoleByName(String name) {
            for (Role role : values()) {
                if (role.toString().equals(name)) {
                    return role;
                }
            }
            return null;
        }

}