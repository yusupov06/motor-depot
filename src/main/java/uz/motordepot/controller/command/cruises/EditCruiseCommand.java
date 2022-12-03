package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CruiseDTO;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.service.contract.CruiseService;
import uz.motordepot.service.contract.DriverService;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.ADD_CRUISE_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class EditCruiseCommand implements Command {

    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);
    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);
    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = ADD_CRUISE_PAGE;
        HttpSession session = request.getSession();
        long current = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
        Optional<CruiseDTO> optionalCruiseDTO = cruiseService.findById(current);

        if (optionalCruiseDTO.isPresent()) {
            CruiseDTO cruiseDTO = optionalCruiseDTO.get();
            int reqsPagination = Integer.parseInt(request.getParameter(CRUISE_REQUESTS_PAGINATION));
            int driversPagination = Integer.parseInt(request.getParameter(CRUISE_DRIVERS_PAGINATION));

            Page<RequestDTO> requestsPage = requestService.findPageByStatus(reqsPagination, PAGE_COUNT, RequestStatus.CREATED);
            Page<DriverDTO> driversPage = driverService.findPageByStatus(driversPagination, PAGE_COUNT, DriverStatus.FREE);

            RequestDTO req = requestService.findById(cruiseDTO.getRequest().getId());
            DriverDTO driver = driverService.findById(cruiseDTO.getDriver().getId()).orElseThrow();

            List<RequestDTO> reqList = new ArrayList<>(requestsPage.getItems());
            List<DriverDTO> drivList = new ArrayList<>(driversPage.getItems());

            reqList.add(0, req);
            drivList.add(0, driver);
            requestsPage.setItems(reqList);
            driversPage.setItems(drivList);

            session.setAttribute(SESSION_CRUISE_REQUEST_PAGE, requestsPage);
            session.setAttribute(SESSION_CRUISE_DRIVER_PAGE, driversPage);
            session.setAttribute(SESSION_ATTRIBUTE_EDITING, cruiseDTO);
        }
        return new Router(page, FORWARD);
    }
}
