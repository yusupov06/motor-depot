package uz.motordepot.controller.command.auth;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static uz.motordepot.controller.navigation.PageNavigation.SIGN_UP;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;


public class SignUpGetCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(SIGN_UP, FORWARD);
    }
}
