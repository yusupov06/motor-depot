package uz.motordepot.controller.command.cars;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarAddDto;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.CarService;
import uz.motordepot.utils.validator.AddCarFormValidator;
import uz.motordepot.utils.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CARS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class FinishEditCarCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = CARS_PAGE;
        Router.PageChangeType type = FORWARD;

        HttpSession session = request.getSession();
        Map<String, String[]> parameterMap = request.getParameterMap();
        FormValidator formValidator = new AddCarFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        if (validationResult.isEmpty()) {

            String carModel = request.getParameter(PARAMETER_CAR_MODEL);
            String carNumber = request.getParameter(PARAMETER_CAR_NUMBER);
            UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
            long carId = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
            boolean edit = carService.edit(carId, new CarAddDto(carModel, carNumber, currentUser.getId()));
            if (edit) {
                Page<CarDTO> allByPage = carService.findByPage(1, PAGE_COUNT);
                session.setAttribute(SESSION_ATTR_PAGE, allByPage);

                session.removeAttribute(SESSION_ATTRIBUTE_EDITING);
                return new Router(page, type);
            }
        } else
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);
        return new Router(page, type);
    }
}
