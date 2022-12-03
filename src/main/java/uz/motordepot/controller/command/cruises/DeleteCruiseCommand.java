package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.CruiseStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.service.contract.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CRUISES_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class DeleteCruiseCommand implements Command {

    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = CRUISES_PAGE;
        HttpSession session = request.getSession();

        String parameter = request.getParameter(PARAMETER_CURRENT_ID);

        if (Objects.isNull(parameter) || parameter.length() == 0) {
            session.setAttribute(DELETING_MESSAGE, "Deleting id can not be blank");
        } else {

            Long id = Long.valueOf(request.getParameter(PARAMETER_CURRENT_ID));

            CruiseStatus status = cruiseService.getStatusById(id);

//            if (!status.equals(CruiseStatus.CREATED)) {
//                session.setAttribute(INVALID_DELETING_MESSAGE, "You can not delete driver because status is " + statusById);
//            } else {
//                driverService.delete(id);
//            }

        }

//        driverService.findByPage(0, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, null);
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, page);
        return new Router(page, REDIRECT);
    }
}
