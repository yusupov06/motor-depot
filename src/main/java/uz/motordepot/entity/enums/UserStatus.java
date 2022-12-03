package uz.motordepot.entity.enums;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Sun 13/11/22 14:49
 */
public enum UserStatus {
    ACTIVE, BLOCKED;

    public static UserStatus defineValue(String status) {
        for (UserStatus value : UserStatus.values()) {
            if (value.name().equals(status.toUpperCase())) return value;
        }

        return BLOCKED;
    }

    public static UserStatus define(String status) {
        for (UserStatus value : values()) {
            if (value.name().equals(status.toUpperCase())) return value;
        }
        return BLOCKED;
    }
}
