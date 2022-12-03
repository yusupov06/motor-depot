package uz.motordepot.controller.command.driver;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.service.contract.DriverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.DRIVERS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class DriversCommand implements Command {

    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = DRIVERS_PAGE;
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        Page<DriverDTO> allByPage = driverService.findAllByPage(pagee, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, allByPage);
        System.out.println("allByPage = " + allByPage.getTotalPages());
        return new Router(page, FORWARD);
    }

}
