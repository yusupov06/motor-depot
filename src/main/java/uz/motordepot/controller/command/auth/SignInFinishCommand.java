package uz.motordepot.controller.command.auth;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.UserStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.UserService;
import uz.motordepot.utils.validator.FormValidator;
import uz.motordepot.utils.validator.auth.SignInValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.HOME;
import static uz.motordepot.controller.navigation.PageNavigation.SIGN_IN;
import static uz.motordepot.controller.router.Router.PageChangeType;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;


public class SignInFinishCommand implements Command {
    private final UserService userService = InstanceHolder.getInstance(UserService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        String page = SIGN_IN;
        HttpSession session = request.getSession();
        Map<String, String[]> parameterMap = request.getParameterMap();
        FormValidator formValidator = new SignInValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        if (validationResult.isEmpty()) {

            String username = request.getParameter(PARAMETER_PHONE_NUMBER);
            String password = request.getParameter(PARAMETER_PASSWORD);

            Optional<UserDTO> optionalUser = userService.authenticate(username, password);

            if (optionalUser.isPresent()) {

                UserDTO user = optionalUser.get();
                if (user.getStatus().equals(UserStatus.ACTIVE.name())) {
                    page = HOME;
                    session.setAttribute(SESSION_ATTRIBUTE_CURRENT_USER, user);
                } else {
                    session.setAttribute(REQ_ATTRIBUTE_USER_BLOCKED, USER_BLOCKED_MESSAGE);
                }
            } else {
                session.setAttribute(REQ_ATTRIBUTE_USER_INVALID, INVALID_USER_MESSAGE);
            }
        } else {
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);
        }

        return new Router(page, FORWARD);
    }
}
