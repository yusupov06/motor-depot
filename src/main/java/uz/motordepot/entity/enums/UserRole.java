package uz.motordepot.entity.enums;

public enum UserRole {
    MANAGER,
    DISPATCHER,
    DRIVER,
    GUEST;


    public static UserRole define(String name) {
        for (UserRole value : values()) {
            if (value.name().equals(name.toUpperCase())){
                return value;
            }
        }
        return GUEST;
    }
}
