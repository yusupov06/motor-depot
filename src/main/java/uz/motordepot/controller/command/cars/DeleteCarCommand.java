package uz.motordepot.controller.command.cars;

import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.router.Router;
import uz.motordepot.dao.contract.CarDao;
import uz.motordepot.entity.enums.CarCondition;
import uz.motordepot.exception.CommandException;
import uz.motordepot.instanceHolder.InstanceHolder;
import uz.motordepot.pagination.Page;
import uz.motordepot.payload.CarDTO;
import uz.motordepot.service.contract.CarService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.controller.navigation.PageNavigation.CARS_PAGE;
import static uz.motordepot.controller.router.Router.PageChangeType.REDIRECT;

public class DeleteCarCommand implements Command {

    private final CarService carService = InstanceHolder.getInstance(CarService.class);
    private final CarDao carDao = InstanceHolder.getInstance(CarDao.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();

        String parameter = request.getParameter(PARAMETER_CURRENT_ID);

        if (Objects.isNull(parameter) || parameter.length() == 0) {
            session.setAttribute(DELETING_MESSAGE, "Deleting id can not be blank");
        }

        Long carId = Long.valueOf(request.getParameter(PARAMETER_CURRENT_ID));

        CarCondition condition = carService.getStatusById(carId);

        if (!condition.equals(CarCondition.NOT_ACTIVE)) {
            session.setAttribute(DELETING_MESSAGE, "You can not delete car because condition is " + condition);
        } else {
            if (carDao.existById(carId, CarDao.EXISTS_BY_ID))
                carService.delete(carId);
            else
                session.setAttribute(DELETING_MESSAGE, "Car not found with this id " + carId);
        }

        Page<CarDTO> allByPage = carService.findByPage(1, PAGE_COUNT);
        session.setAttribute(SESSION_ATTR_PAGE, allByPage);
        return new Router(CARS_PAGE, REDIRECT);
    }
}
