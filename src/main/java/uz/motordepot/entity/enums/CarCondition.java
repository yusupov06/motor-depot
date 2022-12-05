package uz.motordepot.entity.enums;

public enum CarCondition {
    ACTIVE,
    NOT_ACTIVE
    ;

    public static CarCondition define(String condition) {
        for (CarCondition value : values()) {
            if (value.name().equalsIgnoreCase(condition)) return value;
        }
        return NOT_ACTIVE;
    }
}
