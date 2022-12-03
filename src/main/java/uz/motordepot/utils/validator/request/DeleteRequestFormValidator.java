package uz.motordepot.utils.validator.request;

import uz.motordepot.utils.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.DELETING_MESSAGE;
import static uz.motordepot.controller.navigation.AttributeParameterHolder.PARAMETER_CURRENT_ID;

public class DeleteRequestFormValidator implements FormValidator {
    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {
        Map<String, String> invalid = new HashMap<>();
        String[] params = parameters.get(PARAMETER_CURRENT_ID);
        if (Objects.isNull(params) || params.length ==0){
            invalid.put(PARAMETER_CURRENT_ID, DELETING_MESSAGE);
        }
        return null;
    }
}
