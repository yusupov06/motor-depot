package uz.motordepot.controller.command.request;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class EditRequestCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = REQUESTS_PAGE;
        HttpSession session = request.getSession();
        Long requestId = Long.valueOf(request.getParameter(PARAMETER_CURRENT_ID));
        RequestDTO editing = requestService.findById(requestId);
        Page<RequestDTO> byPage = requestService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        session.setAttribute(SESSION_ATTRIBUTE_EDITING, editing);
        return new Router(page, REDIRECT);
    }
}
