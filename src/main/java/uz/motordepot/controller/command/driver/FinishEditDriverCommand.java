package uz.motordepot.controller.command.driver;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RegisterDriverDTO;
import uz.motordepot.service.contract.CarService;
import uz.motordepot.service.contract.DriverService;
import uz.motordepot.utils.validator.EditDriverFormValidator;
import uz.motordepot.utils.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.DRIVERS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class FinishEditDriverCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);
    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String page = DRIVERS_PAGE;
        Router.PageChangeType type = FORWARD;

        HttpSession session = request.getSession();

        Map<String, String[]> parameterMap = request.getParameterMap();

        FormValidator formValidator = new EditDriverFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        long id = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));

        if (driverService.findById(id).isEmpty())
            validationResult.put("driver", "Driver not found");

        long carId = Long.parseLong(request.getParameter(PARAMETER_DRIVER_CAR_ID));

        if (carService.getStatusById(carId).equals(CarCondition.ACTIVE)) {
            validationResult.put(PARAMETER_DRIVER_CAR_ID,
                    "You can not add this car because it's condition is " + CarCondition.ACTIVE.name());
        }

        if (validationResult.isEmpty()) {

            String firstName = request.getParameter(PARAMETER_DRIVER_FIRSTNAME);
            String lastName = request.getParameter(PARAMETER_DRIVER_LASTNAME);
            String driverEmail = request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER);
            String password = request.getParameter(PARAMETER_DRIVER_PASSWORD);

            RegisterDriverDTO registerDriverDTO;
            if (password.equals("OLD_PASSWORD")) {
                registerDriverDTO = new RegisterDriverDTO(firstName, lastName, driverEmail, carId);
            } else
                registerDriverDTO = new RegisterDriverDTO(firstName, lastName, password, driverEmail, carId);

            driverService.edit(id, registerDriverDTO);

        } else
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);

        Page<DriverDTO> byPage = driverService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        return new Router(page, type);
    }
}
