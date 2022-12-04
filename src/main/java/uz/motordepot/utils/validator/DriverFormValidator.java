package uz.motordepot.utils.validator;

import uz.motordepot.instanceHolder.InstanceHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;

public class DriverFormValidator implements FormValidator {

    private final PatternValidator validator = InstanceHolder.getInstance(PatternValidator.class);

    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {
        Map<String, String> validationRes = new HashMap<>();
        checker(parameters, validationRes, PARAMETER_DRIVER_FIRSTNAME, INVALID_DRIVER_FIRSTNAME);
        checker(parameters, validationRes, PARAMETER_DRIVER_LASTNAME, INVALID_DRIVER_LASTNAME);
        checker(parameters, validationRes, PARAMETER_DRIVER_PHONE_NUMBER, INVALID_DRIVER_PHONE);
        if (parameters.get(PARAMETER_DRIVER_PHONE_NUMBER) != null
                && !validator.validPhoneNumber(parameters.get(PARAMETER_DRIVER_PHONE_NUMBER)[0]))
            validationRes.put(PARAMETER_DRIVER_PHONE_NUMBER, "invalid phone");

        checker(parameters, validationRes, PARAMETER_DRIVER_PASSWORD, INVALID_DRIVER_PASSWORD);
        checker(parameters, validationRes, PARAMETER_DRIVER_CAR_ID, INVALID_DRIVER_CAR_ID);

        return validationRes;
    }

    private void checker(Map<String, String[]> parameters, Map<String, String> validationRes, String param, String error) {
        if (parameters.get(param) == null || parameters.get(param).length == 0 || Objects.equals(parameters.get(param)[0], "")) {
            validationRes.put(param, error);
        }
    }

}
