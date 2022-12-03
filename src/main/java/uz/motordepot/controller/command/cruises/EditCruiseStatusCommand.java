package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.CruiseDao;
import uz.motordepot.entity.enums.PermissionEnum;
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

public class EditCruiseStatusCommand implements Command {

    private final CruiseDao cruiseDao = InstanceHolder.getInstance(CruiseDao.class);
    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        long page = Long.parseLong(request.getParameter(PAGINATION));
        long cruiseId = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
        String changedStatus = request.getParameter("status");
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);

        if (currentUser.getPermissions().contains(PermissionEnum.EDIT_CRUISE_STATUS.name())) {
            cruiseDao.changeStatusById(changedStatus, cruiseId);
            Page<CruiseDTO> byPage;
            if (currentUser.getRole().equals(UserRole.MANAGER.name()))
                byPage = cruiseService.findByPage((int) page, PAGE_COUNT);
            else {
                byPage = cruiseService.findByPageAndDriver((int) page, PAGE_COUNT, currentUser.getId());
            }
            session.setAttribute(SESSION_ATTR_PAGE, byPage);
        }

        return new Router(CRUISES_PAGE, REDIRECT);

    }
}
