package uz.motordepot.controller.command.request;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class EditRequestCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String parameter = request.getParameter(PARAMETER_CURRENT_ID);

        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        if (Objects.isNull(parameter) || parameter.length() == 0) {
            session.setAttribute(EDITING_MESSAGE, "EDITING id can not be blank");
        } else {
            long requestId = Long.parseLong(parameter);
            RequestDTO editing = requestService.getByIdAndAdder(requestId, currentUser.getId());
            session.setAttribute(SESSION_ATTRIBUTE_EDITING, editing);
        }
        Commons.setRequestsPageByRoleToSession(session, 1);
        return new Router(REQUESTS_PAGE, REDIRECT);
    }


}
