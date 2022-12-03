package uz.motordepot.controller.command.driver;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.service.contract.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.ADD_DRIVER_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class AddDriverCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ADD_DRIVER_PAGE;
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        Page<CarDTO> notActiveCars = carService.findByPageAndStatus(pagee, PAGE_COUNT, CarCondition.NOT_ACTIVE.name());
        session.setAttribute(SESSION_ATTR_PAGE, notActiveCars);

        return new Router(page, FORWARD);
    }
}
