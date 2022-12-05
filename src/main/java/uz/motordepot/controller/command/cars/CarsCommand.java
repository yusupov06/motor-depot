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
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class CarsCommand implements Command {
    private final CarService carService = InstanceHolder.getInstance(CarService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = CARS_PAGE;
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        Page<CarDTO> carDTOPage = carService.findByPage(pagee, PAGE_COUNT);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        session.setAttribute(SESSION_ATTR_PAGE, carDTOPage);
        return new Router(page, FORWARD);
    }
}
