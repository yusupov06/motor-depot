package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.service.contract.DriverService;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.ADD_CRUISE_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class AddCruiseCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);
    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String page = ADD_CRUISE_PAGE;
        HttpSession session = request.getSession();
        int reqsPagination = Integer.parseInt(request.getParameter(CRUISE_REQUESTS_PAGINATION));
        int driversPagination = Integer.parseInt(request.getParameter(CRUISE_DRIVERS_PAGINATION));

        Page<RequestDTO> requestsPage = requestService.findPageByStatus(reqsPagination, PAGE_COUNT, RequestStatus.CREATED);
        Page<DriverDTO> driversPage = driverService.findPageByStatus(driversPagination, PAGE_COUNT, DriverStatus.FREE);

        session.setAttribute(SESSION_CRUISE_REQUEST_PAGE, requestsPage);
        session.setAttribute(SESSION_CRUISE_DRIVER_PAGE, driversPage);

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);

    }
}
