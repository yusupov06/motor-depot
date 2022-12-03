package uz.motordepot.controller.command;

import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.PageNavigation.DEFAULT;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;


public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String page = DEFAULT;



        return new Router(page, FORWARD);
    }
}
