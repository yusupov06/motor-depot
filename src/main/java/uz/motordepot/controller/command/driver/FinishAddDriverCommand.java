package uz.motordepot.controller.command.driver;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RegisterDriverDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.CarService;
import uz.motordepot.service.contract.DriverService;
import uz.motordepot.utils.validator.DriverFormValidator;
import uz.motordepot.utils.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.DRIVERS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class FinishAddDriverCommand implements Command {

    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);
    private final CarService carService = InstanceHolder.getInstance(CarService.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        Map<String, String[]> parameterMap = request.getParameterMap();
        FormValidator formValidator = new DriverFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        if (validationResult.isEmpty()) {

            if (request.getParameter(PARAMETER_DRIVER_CAR_ID) != null) {
                long carId = Long.parseLong(request.getParameter(PARAMETER_DRIVER_CAR_ID));

                if (carService.getStatusById(carId).equals(CarCondition.ACTIVE)) {
                    validationResult.put(PARAMETER_DRIVER_CAR_ID,
                            "You can not add this car because it's condition is " + CarCondition.ACTIVE.name());
                }
            }

            if (request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER) != null) {
                if (userDao.existsByPhoneNumber(request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER))) {
                    validationResult.put(PARAMETER_DRIVER_PHONE_NUMBER,
                            "this " + request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER) + " phone number already taken ");
                }
            }
            if (validationResult.isEmpty()) {
                String firstName = request.getParameter(PARAMETER_DRIVER_FIRSTNAME);
                String lastName = request.getParameter(PARAMETER_DRIVER_LASTNAME);
                String driverPhoneNumber = request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER);
                String password = request.getParameter(PARAMETER_DRIVER_PASSWORD);

                UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
                long carId = Long.parseLong(request.getParameter(PARAMETER_DRIVER_CAR_ID));
                driverService.add(new RegisterDriverDTO(firstName, lastName, password, driverPhoneNumber, carId, currentUser.getId()));
            }
        }

        if (!validationResult.isEmpty()) {
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);
        }

        Page<DriverDTO> byPage = driverService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        return new Router(DRIVERS_PAGE, REDIRECT);

    }
}
