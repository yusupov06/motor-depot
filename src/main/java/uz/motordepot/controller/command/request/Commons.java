package uz.motordepot.controller.command.request;

import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.RequestDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.RequestService;

import javax.servlet.http.HttpSession;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;

public class Commons {

    private static final RequestService requestService = InstanceHolder.getInstance(RequestService.class);

    static void setRequestsPageByRoleToSession(HttpSession session, int page) {
        UserDTO currentUser = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        Page<RequestDTO> requestDTOPage = null;
        if (currentUser.getRole().equals(UserRole.MANAGER.name()))
            requestDTOPage = requestService.findByPage(page, PAGE_COUNT);
        else if (currentUser.getRole().equals(UserRole.DISPATCHER.name()))
            requestDTOPage = requestService.findByPageAndAdderId(page, PAGE_COUNT, currentUser.getId());

        session.setAttribute(SESSION_ATTR_PAGE, requestDTOPage);
    }
}
