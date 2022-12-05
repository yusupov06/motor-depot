package uz.motordepot.controller.command.request;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.payload.RequestAddDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.RequestService;
import uz.motordepot.utils.validator.FormValidator;
import uz.motordepot.utils.validator.request.AddRequestFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.REQUESTS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class AddRequestCommand implements Command {

    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = REQUESTS_PAGE;
        Router.PageChangeType type = FORWARD;

        HttpSession session = request.getSession();
        Map<String, String[]> parameterMap = request.getParameterMap();
        FormValidator formValidator = new AddRequestFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        if (validationResult.isEmpty()) {

            String name = request.getParameter(PARAMETER_REQUEST_NAME);
            String from = request.getParameter(PARAMETER_REQUEST_FROM);
            String to = request.getParameter(PARAMETER_REQUEST_TO);
            String charac = request.getParameter(PARAMETER_REQUEST_CHARAC);
            UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
            boolean add = requestService.add(new RequestAddDTO(name, from, to, charac, currentUser.getId()));
            if (add) {

                Commons.setRequestsPageByRoleToSession(session, 1);
                return new Router(page, type);
            }
        } else {
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);
        }

        return new Router(page, type);
    }
}
