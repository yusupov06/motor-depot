package uz.motordepot.controller.filter;

import uz.motordepot.controller.command.CommandType;
import uz.motordepot.controller.navigation.PageNavigation;
import uz.motordepot.entity.enums.UserRole;
import uz.motordepot.payload.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static uz.motordepot.controller.command.CommandType.*;
import static uz.motordepot.controller.navigation.AttributeParameterHolder.*;
import static uz.motordepot.entity.enums.UserRole.*;


@WebFilter(
        filterName = "UserPermissionFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = "/controller"
)
public class UserPermissionFilter implements Filter {

    private Map<UserRole, EnumSet<CommandType>> allowedCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedCommands = Map.of(

                GUEST, EnumSet.of(
                        SIGN_IN,
                        FINISH_SIGN_IN
                ),
                MANAGER, EnumSet.of(

                        SIGN_IN,
                        SIGN_UP,
                        FINISH_SIGN_IN,
                        HOME,
                        DEFAULT,

                        REQUESTS,
                        ADD_REQUEST,
                        DELETE_REQUEST,
                        EDIT_REQUEST,
                        FINISH_EDIT_REQUEST,

                        CRUISES,
                        ADD_CRUISE_PAGE,
                        ADD_CRUISE,
                        DELETE_CRUISE,
                        EDIT_CRUISE,
                        FINISH_EDIT_CRUISE,
                        EDIT_CRUISE_STATUS,

                        CARS,
                        ADD_CAR,
                        DELETE_CAR,
                        EDIT_CAR,
                        FINISH_EDIT_CAR,

                        DRIVERS,
                        ADD_DRIVER,
                        ADD_DRIVER_PAGE,
                        DELETE_DRIVER,
                        EDIT_DRIVER_PAGE,
                        EDIT_DRIVER,

                        LOG_OUT
                ),

                DISPATCHER, EnumSet.of(
                        HOME,
                        SIGN_IN,
                        FINISH_SIGN_IN,
                        REQUESTS,
                        ADD_REQUEST,
                        DELETE_REQUEST,
                        EDIT_REQUEST,
                        FINISH_EDIT_REQUEST
                ),

                DRIVER, EnumSet.of(
                        HOME,
                        SIGN_IN,
                        FINISH_SIGN_IN,
                        SIGN_UP,
                        CRUISES,
                        EDIT_CRUISE_STATUS,
                        EDIT_CRUISE_NOTE
                )
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String commandStr = request.getParameter(PARAMETER_COMMAND);
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute(SESSION_ATTRIBUTE_CURRENT_USER);
        UserRole role = user != null && user.getRole() != null ? UserRole.define(user.getRole()) : GUEST;

        CommandType commandType = defineCommandType(commandStr);

        EnumSet<CommandType> allowed = allowedCommands.get(role);

        if (!allowed.contains(commandType)) {
            session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, PageNavigation.SIGN_IN);
            response.sendRedirect(request.getContextPath() + PageNavigation.SIGN_IN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
        System.out.println(" destroy method called ");
    }
}
