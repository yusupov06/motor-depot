package uz.motordepot.controller.command.cars;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.service.contract.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CARS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class EditCarCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = CARS_PAGE;
        HttpSession session = request.getSession();
        Long carId = Long.valueOf(request.getParameter(PARAMETER_CURRENT_ID));
        CarDTO carDTO = carService.findById(carId);
        Page<CarDTO> allByPage = carService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, allByPage);

        session.setAttribute(SESSION_ATTRIBUTE_EDITING, carDTO);
        return new Router(page, REDIRECT);
    }
}
