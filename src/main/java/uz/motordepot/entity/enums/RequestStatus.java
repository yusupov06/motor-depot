package uz.motordepot.entity.enums;

/**
 * Me: muhammadqodir
 * Project: Motor-depot/IntelliJ IDEA
 * Date:Wed 02/11/22 17:21
 */
public enum RequestStatus {

    CREATED,
    APPROVED,
    COMPLETED;

    public static RequestStatus define(String s) {
        for (RequestStatus value : values()) {
            if (value.name().equalsIgnoreCase(s)){
                return value;
            }
        }
        return null;
    }
}
