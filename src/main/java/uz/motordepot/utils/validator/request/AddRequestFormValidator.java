package uz.motordepot.utils.validator.request;

import uz.motordepot.utils.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;

public class AddRequestFormValidator implements FormValidator {
    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {

        Map<String, String> validationRes = new HashMap<>();
        checker(parameters, validationRes, PARAMETER_REQUEST_NAME, INVALID_REQUEST_NAME);
        checker(parameters, validationRes, PARAMETER_REQUEST_FROM, INVALID_REQUEST_FROM);
        checker(parameters, validationRes, PARAMETER_REQUEST_TO, INVALID_REQUEST_TO);
        return validationRes;
    }

    private void checker(Map<String, String[]> parameters, Map<String, String> validationRes, String param, String error) {
        if (parameters.get(param) == null || parameters.get(param).length == 0 || Objects.equals(parameters.get(param)[0], "")) {
            validationRes.put(param, error);
        }
    }

}
