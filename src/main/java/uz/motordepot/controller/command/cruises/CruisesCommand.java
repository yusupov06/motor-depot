package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CruiseDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CRUISES_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class CruisesCommand implements Command {

    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String page = CRUISES_PAGE;
        HttpSession session = request.getSession();
        int pagee = Integer.parseInt(request.getParameter(PAGINATION));
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        Page<CruiseDTO> byPage;
        if (currentUser.getRole().equals(UserRole.DRIVER.name())){
            byPage = cruiseService.findByPageAndDriver(pagee, PAGE_COUNT, currentUser.getId());
        } else {
            byPage = cruiseService.findByPage(pagee, PAGE_COUNT);
        }

        session.setAttribute(SESSION_ATTR_PAGE, byPage);

        return new Router(page, REDIRECT);
    }
}
