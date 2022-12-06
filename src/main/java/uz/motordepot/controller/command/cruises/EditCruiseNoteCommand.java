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
import java.util.Optional;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CRUISES_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class EditCruiseNoteCommand implements Command {

    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        String note = request.getParameter(PARAMETER_CRUISE_NOTE);
        long cruiseId = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);

        if (currentUser.getRole().equalsIgnoreCase(UserRole.DRIVER.name())) {
            Optional<CruiseDTO> byId = cruiseService.findById(cruiseId);
            if (byId.isPresent()) {
                cruiseService.addNote(cruiseId, note);
            }
        }

        Page<CruiseDTO> byPage;
        if (currentUser.getRole().equals(UserRole.MANAGER.name()))
            byPage = cruiseService.findByPage(1, PAGE_COUNT);
        else {
            byPage = cruiseService.findByPageAndDriver(1, PAGE_COUNT, currentUser.getId());
        }
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        return new Router(CRUISES_PAGE, FORWARD);
    }
}
