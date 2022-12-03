package uz.motordepot.controller.command.auth;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.PageNavigation.HOME;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

/**
 * Log out command
 */
public class LogOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(HOME, REDIRECT);
    }
}
