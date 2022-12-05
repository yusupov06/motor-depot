package uz.motordepot.controller.command.request;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.PAGINATION;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class RequestCommand implements Command {

    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        Commons.setRequestsPageByRoleToSession(session, pagee);
        return new Router(REQUESTS_PAGE, FORWARD);
    }
}
