package uz.motordepot.controller.filter;

import uz.motordepot.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static uz.motordepot.controller.command.CommandType.*;
import static uz.motordepot.controller.navigation.AttributeParameterHolder.PARAMETER_COMMAND;
import static uz.motordepot.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_EDITING;

@WebFilter(
        filterName = "EditAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = "/controller"
)
public class EditAttributeCleanerFilter implements Filter {

    private List<CommandType> allowedCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedCommands = List.of(
                FINISH_EDIT_REQUEST,
                FINISH_EDIT_CAR,
                FINISH_EDIT_CRUISE,
                EDIT_CRUISE,
                EDIT_DRIVER,
                EDIT_DRIVER_PAGE,
                EDIT_CRUISE_STATUS
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String commandStr = request.getParameter(PARAMETER_COMMAND);
        HttpSession session = request.getSession();
        CommandType commandType = defineCommandType(commandStr);

        if (!allowedCommands.contains(commandType))
            session.removeAttribute(SESSION_ATTRIBUTE_EDITING);
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
