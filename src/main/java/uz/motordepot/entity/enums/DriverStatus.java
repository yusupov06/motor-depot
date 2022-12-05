package uz.motordepot.entity.enums;

public enum DriverStatus {
    FREE, BUSY;

    public static DriverStatus define(String status) {
        for (DriverStatus value : values()) {
            if (value.name().equals(status.toUpperCase())) return value;
        }

        return FREE;
    }
}
