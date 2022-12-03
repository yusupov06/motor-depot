package uz.motordepot.utils.validator.auth;

import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.utils.validator.FormValidator;
import uz.motordepot.utils.validator.PatternValidator;

import java.util.HashMap;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;


public class SignInValidator implements FormValidator {

    private final PatternValidator validator = InstanceHolder.getInstance(PatternValidator.class);

    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {

        Map<String, String> validationResult = new HashMap<>();

        if (parameters.get(PARAMETER_PHONE_NUMBER) == null
                || parameters.get(PARAMETER_PHONE_NUMBER).length == 0
                || !validator.validPhoneNumber(parameters.get(PARAMETER_PHONE_NUMBER)[0])
        ) {
            validationResult.put(PARAMETER_PHONE_NUMBER, INVALID_PHONE_NUMBER_MESSAGE);
        }

        if (parameters.get(PARAMETER_PASSWORD) == null
                || parameters.get(PARAMETER_PASSWORD).length == 0
                || !validator.validPassword(parameters.get(PARAMETER_PASSWORD)[0])
        ) {
            validationResult.put(PARAMETER_PASSWORD, INVALID_PASSWORD_MESSAGE);
        }

        return validationResult;
    }
}
