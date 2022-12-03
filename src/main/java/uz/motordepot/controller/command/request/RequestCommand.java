package uz.motordepot.controller.command.request;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class RequestCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {
        String page = REQUESTS_PAGE;
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        Page<RequestDTO> requestDTOPage = null;
        if (currentUser.getRole().equals(UserRole.MANAGER.name()))
             requestDTOPage = requestService.findByPage(pagee, PAGE_COUNT);
        else if (currentUser.getRole().equals(UserRole.DISPATCHER.name()))
            requestDTOPage = requestService.findByPageAndAdderId(pagee, PAGE_COUNT, currentUser.getId());
        session.setAttribute(SESSION_ATTR_PAGE, requestDTOPage);
        return new Router(page, FORWARD);
    }
}
