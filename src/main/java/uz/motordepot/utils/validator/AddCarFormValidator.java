package uz.motordepot.utils.validator;

import uz.motordepot.instanceHolder.InstanceHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;

public class AddCarFormValidator implements FormValidator {

    private final PatternValidator validator = InstanceHolder.getInstance(PatternValidator.class);

    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {

        Map<String, String> validationResult = new HashMap<>();

        if (parameters.get(PARAMETER_CAR_MODEL) == null
                || parameters.get(PARAMETER_CAR_MODEL).length == 0
                || !validator.validCarModel(parameters.get(PARAMETER_CAR_MODEL)[0])) {
            validationResult.put(PARAMETER_CAR_MODEL, INVALID_CAR_MODEL);
        }

        if (parameters.get(PARAMETER_CAR_NUMBER) == null
                || parameters.get(PARAMETER_CAR_NUMBER).length == 0
                || Objects.equals(parameters.get(PARAMETER_CAR_NUMBER)[0], "")) {
            validationResult.put(PARAMETER_CAR_NUMBER, INVALID_CAR_NUMBER);
        }

        return validationResult;
    }
}
