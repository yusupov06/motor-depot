package uz.motordepot.controller.command.driver;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.UserDao;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RegisterDriverDTO;
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

public class FinishEditDriverCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);
    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);
    private final UserDao userDao = InstanceHolder.getInstance(UserDao.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        Map<String, String[]> parameterMap = request.getParameterMap();

        FormValidator formValidator = new DriverFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);
        DriverDTO currentDriver = null;
        if (request.getParameter(PARAMETER_CURRENT_ID) != null) {

            long id = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
            currentDriver = driverService.findById(id).orElse(null);

        } else
            validationResult.put(PARAMETER_CURRENT_ID, "Driver id can not be null");

        if (currentDriver == null)
            validationResult.put(PARAMETER_CURRENT_ID, "Driver not found ");

        if (validationResult.isEmpty()) {


            if (request.getParameter(PARAMETER_DRIVER_CAR_ID) != null) {
                long carId = Long.parseLong(request.getParameter(PARAMETER_DRIVER_CAR_ID));

                if (currentDriver.getCar().getId() != carId && carService.getStatusById(carId).equals(CarCondition.ACTIVE)) {
                    validationResult.put(PARAMETER_DRIVER_CAR_ID,
                            "You can not add this car because it's condition is " + CarCondition.ACTIVE.name());
                }
            }

            if (request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER) != null) {

                if (!currentDriver
                        .getUser()
                        .getPhoneNumber()
                        .equals(request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER))
                        && !userDao
                        .existsByPhoneNumber(request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER)))
                    validationResult.put(PARAMETER_DRIVER_PHONE_NUMBER,
                            "this " + request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER) + " phone number already taken ");


            }

            if (validationResult.isEmpty()) {

                String firstName = request.getParameter(PARAMETER_DRIVER_FIRSTNAME);
                String lastName = request.getParameter(PARAMETER_DRIVER_LASTNAME);
                String driverEmail = request.getParameter(PARAMETER_DRIVER_PHONE_NUMBER);
                String password = request.getParameter(PARAMETER_DRIVER_PASSWORD);

                long carId = Long.parseLong(request.getParameter(PARAMETER_DRIVER_CAR_ID));

                RegisterDriverDTO registerDriverDTO;
                if (password.equals("OLD_PASSWORD")) {
                    registerDriverDTO = new RegisterDriverDTO(firstName, lastName, driverEmail, carId);
                } else
                    registerDriverDTO = new RegisterDriverDTO(firstName, lastName, password, driverEmail, carId);

                driverService.edit(currentDriver.getId(), registerDriverDTO);

            }
        }
        if (!validationResult.isEmpty())
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);

        Page<DriverDTO> byPage = driverService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        return new Router(DRIVERS_PAGE, FORWARD);
    }
}
