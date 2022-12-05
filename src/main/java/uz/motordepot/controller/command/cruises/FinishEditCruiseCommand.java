package uz.motordepot.controller.command.cruises;

import org.jetbrains.annotations.NotNull;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.CruiseDao;
import uz.motordepot.entity.enums.DriverStatus;
import uz.motordepot.entity.enums.RequestStatus;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CruiseAddDTO;
import uz.motordepot.payload.CruiseDTO;
import uz.motordepot.payload.UserDTO;
import uz.motordepot.service.contract.CruiseService;
import uz.motordepot.service.contract.RequestService;
import uz.motordepot.utils.validator.AddCruiseFormValidator;
import uz.motordepot.utils.validator.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CRUISES_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.FORWARD;

public class FinishEditCruiseCommand implements Command {

    private final CruiseService cruiseService = InstanceHolder.getInstance(CruiseService.class);
    private final RequestService requestService = InstanceHolder.getInstance(RequestService.class);
    private final CruiseDao cruiseDao = InstanceHolder.getInstance(CruiseDao.class);


    @Override
    public Router execute(@NotNull HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();

        Map<String, String[]> parameterMap = request.getParameterMap();
        FormValidator formValidator = new AddCruiseFormValidator();
        Map<String, String> validationResult = formValidator.validate(parameterMap);

        long current = Long.parseLong(request.getParameter(PARAMETER_CURRENT_ID));
        Optional<CruiseDTO> optionalCruiseDTO = cruiseService.findById(current);

        if (validationResult.isEmpty() && optionalCruiseDTO.isPresent()) {
            CruiseDTO cruiseDTO = optionalCruiseDTO.get();
            long requestId = Long.parseLong(request.getParameter(PARAMETER_CRUISE_REQUEST_ID));
            long driverId = Long.parseLong(request.getParameter(PARAMETER_CRUISE_DRIVER_ID));

            if (requestId != cruiseDTO.getRequest().getId()) {
                RequestStatus statusById = requestService.getStatusById(requestId);
                if (!statusById.equals(RequestStatus.CREATED))
                    validationResult.put(PARAMETER_CRUISE_REQUEST_ID, "You can not add request with status " + statusById);
            }

            if (driverId != cruiseDTO.getDriver().getId()) {

                DriverStatus driverStatusFromCruises = cruiseDao.findDriverStatusFromCruises(driverId);
                if (!driverStatusFromCruises.equals(DriverStatus.FREE)) {
                    validationResult.put(PARAMETER_CRUISE_DRIVER_ID, "You can not choose this driver because his status is " + driverStatusFromCruises);
                }

            }

            UserDTO user = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
            boolean add = cruiseService.edit(cruiseDTO.getId(), new CruiseAddDTO(driverId, requestId, user.getId()));
            if (add) {
                Page<CruiseDTO> byPage = cruiseService.findByPage(1, PAGE_COUNT);
                session.setAttribute(SESSION_ATTR_PAGE, byPage);
            }

        }

        if (!validationResult.isEmpty())
            session.setAttribute(REQ_ATTRIBUTE_FORM_INVALID, validationResult);

        return new Router(CRUISES_PAGE, FORWARD);

    }
}
