package uz.motordepot.utils.validator;

import java.util.HashMap;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;

public class AddCruiseFormValidator implements FormValidator {
    @Override
    public Map<String, String> validate(Map<String, String[]> parameters) {

        String[] requestParams = parameters.get(PARAMETER_CRUISE_REQUEST_ID);
        String[] driverParams = parameters.get(PARAMETER_CRUISE_DRIVER_ID);

        Map<String, String> invalidRes = new HashMap<>();

        if (requestParams == null || requestParams.length == 0){
            invalidRes.put(PARAMETER_CRUISE_REQUEST_ID, INVALID_CRUISE_REQUEST_ID);
        }

        if (driverParams == null || driverParams.length == 0){
            invalidRes.put(PARAMETER_CRUISE_DRIVER_ID, INVALID_CRUISE_DRIVER_ID);
        }

        return invalidRes;
    }
}
