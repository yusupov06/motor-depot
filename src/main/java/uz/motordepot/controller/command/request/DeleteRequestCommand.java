package uz.motordepot.controller.command.request;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class DeleteRequestCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        String parameter = request.getParameter(PARAMETER_CURRENT_ID);

        if (Objects.isNull(parameter) || parameter.length() == 0) {
            session.setAttribute(DELETING_MESSAGE, "Deleting id can not be blank");
        }

        Long id = Long.valueOf(parameter);

        RequestStatus statusById = requestService.getStatusById(id);

        if (!statusById.equals(RequestStatus.CREATED)) {
            session.setAttribute(DELETING_MESSAGE, "You can not delete request because status is " + statusById);
        } else {
            requestService.delete(id);
            session.setAttribute(DELETING_MESSAGE, "Deleted successfully");
        }

        Commons.setRequestsPageByRoleToSession(session,1);

        return new Router(REQUESTS_PAGE, REDIRECT);
    }
}
