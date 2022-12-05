package uz.motordepot.controller.command.driver;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.exception.ServiceException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.payload.DriverDTO;
import uz.motordepot.service.contract.CarService;
import uz.motordepot.service.contract.DriverService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.ADD_DRIVER_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class EditDriverCommand implements Command {

    private final DriverService driverService = InstanceHolder.getInstance(DriverService.class);
    private final CarService carService = InstanceHolder.getInstance(CarService.class);

    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {
        String page = ADD_DRIVER_PAGE;
        HttpSession session = request.getSession();

        String parameter = request.getParameter(PARAMETER_CURRENT_ID);

        if (Objects.isNull(parameter) || parameter.length() == 0) {
            session.setAttribute(DELETING_MESSAGE, "Editing id can not be blank");
        } else {

            long id = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
            DriverDTO driver = driverService
                    .findById(id)
                    .orElseThrow(() -> ServiceException.throwExc("Driver not found with id " + id));
            int pagee = Integer.parseInt(request.getParameter(PAGINATION));
            Page<CarDTO> byPageAndStatus = carService.findByPageAndStatus(pagee, PAGE_COUNT, CarCondition.NOT_ACTIVE.name());

            ArrayList<CarDTO> carDTOS = new ArrayList<>(byPageAndStatus.getItems());
            carDTOS.add(0, driver.getCar());
            byPageAndStatus.setItems(carDTOS);

            session.setAttribute(SESSION_ATTRIBUTE_EDITING, driver);
            session.setAttribute(SESSION_ATTR_PAGE, byPageAndStatus);

        }

        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, FORWARD);
    }
}
