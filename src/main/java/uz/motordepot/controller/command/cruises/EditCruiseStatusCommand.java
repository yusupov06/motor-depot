package uz.motordepot.controller.command.cruises;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.CruiseDao;
import uz.motordepot.entity.enums.CruiseStatus;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CRUISES_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class EditCruiseStatusCommand implements Command {

    private final CruiseDao cruiseDao = InstanceHolder.getInstance(CruiseDao.class);
    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        long page = Long.parseLong(request.getParameter(PAGINATION));
        long cruiseId = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
        String changedStatusStr = request.getParameter(PARAMETER_CRUISE_STATUS);
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        Map<String, String> invalid = new HashMap<>();

        if (currentUser.getPermissions().contains(PermissionEnum.EDIT_CRUISE_STATUS.name())) {
            Optional<CruiseDTO> byId = cruiseService.findById(cruiseId);

            if (byId.isPresent()) {

                CruiseDTO cruiseDTO = byId.get();

                if (currentUser.getRole().equals(UserRole.MANAGER.name())
                        || Objects.equals(cruiseDTO.getDriver().getUser().getId(), currentUser.getId())) {

                    CruiseStatus currentStatus = null;
                    CruiseStatus changedStatus = null;

                    for (CruiseStatus value : CruiseStatus.values()) {
                        if (value.name().equalsIgnoreCase(cruiseDTO.getStatus())) {
                            currentStatus = value;
                        }
                        if (value.name().equalsIgnoreCase(changedStatusStr)) {
                            changedStatus = value;
                        }
                    }

                    if (currentStatus != null && changedStatus != null) {

                        if (Objects.equals(changedStatus, CruiseStatus.COMPLETED)) {

                            if (currentUser.getRole().equals(UserRole.MANAGER.name()))
                                cruiseDao.changeStatusById(CruiseStatus.COMPLETED.name(), cruiseId);
                            else
                                invalid.put("canChange", "You can not change to COMPLETED ");

                        } else {
                            if (currentStatus.ordinal() + 1 == changedStatus.ordinal()) {
                                cruiseDao.changeStatusById(changedStatus.name(), cruiseId);
                            } else
                                invalid.put("canChange", "You can not change status because current status is " + currentStatus.name() + " changing to " + changedStatusStr);
                        }


                    } else {
                        if (currentStatus == null) {
                            invalid.put("status", "Invalid status");
                        }

                        if (changedStatus == null)
                            invalid.put("changedStatus", "changed status is invalid");
                    }

                } else invalid.put("permission", "You have no permission to change");

            } else
                invalid.put(PARAMETER_CURRENT_ID, "Cruise not found with this id " + cruiseId);
        } else
            invalid.put("permission", "You have no permission");

        Page<CruiseDTO> byPage;
        if (currentUser.getRole().equals(UserRole.MANAGER.name()))
            byPage = cruiseService.findByPage((int) page, PAGE_COUNT);
        else {
            byPage = cruiseService.findByPageAndDriver((int) page, PAGE_COUNT, currentUser.getId());
        }
        session.setAttribute(SESSION_ATTR_PAGE, byPage);
        session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, invalid);
        return new Router(CRUISES_PAGE, FORWARD);

    }
}
