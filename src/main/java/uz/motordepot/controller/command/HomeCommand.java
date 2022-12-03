package uz.motordepot.controller.command;

import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.PageNavigation.HOME;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Sun 13/11/22 15:19
 */
public class HomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = HOME;

        return new Router(page, REDIRECT);
    }
}
