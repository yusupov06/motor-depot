package uz.motordepot.utils.validator;

import uz.motordepot.entity.enums.CarModel;

public final class PatternValidator {

    public static final int EMAIL_MAX_LENGTH = 45;
    public static final String EMAIL_PATTERN = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    public static final String PASSWORD_PATTERN = "^[\\p{Alnum}\\p{Punct}]{8,20}$";


    public boolean validEmail(String email) {
        return email != null && email.length() < EMAIL_MAX_LENGTH && email.matches(EMAIL_PATTERN);
    }

    public boolean validPassword(String password) {
        return true;
//        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public boolean validCarModel(String model) {
        return !CarModel.define(model).equals(CarModel.NOT_SET);
    }

    public boolean validPhoneNumber(String number) {
        if (number.length() != 13 || !number.startsWith("+998"))
            return false;
        for (int i = 1; i < number.toCharArray().length; i++)
            if (!Character.isDigit(number.charAt(i))) return false;
        return true;
    }
}
