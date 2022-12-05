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
import static uz.motordepot.controller.navigation.AttributeParameterHolder.REQ_ATTRIBUTE_FORM_INVALID;

@WebFilter(
        filterName = "ValidationAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = "/controller"
)
public class ValidationAttributeCleanerFilter implements Filter {


    private List<CommandType> allowedCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        allowedCommands = List.of(
                FINISH_EDIT_REQUEST,
                FINISH_EDIT_CAR,
                ADD_CRUISE,
                ADD_CRUISE_PAGE,
                EDIT_CRUISE,
                EDIT_DRIVER_PAGE,
                FINISH_EDIT_CRUISE
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String commandStr = request.getParameter(PARAMETER_COMMAND);
        HttpSession session = request.getSession();
        CommandType commandType = defineCommandType(commandStr);

        if (!allowedCommands.contains(commandType))
            session.removeAttribute(REQ_ATTRIBUTE_FORM_INVALID);
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}
