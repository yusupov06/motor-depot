package uz.motordepot.controller.filter;

import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.DELETING_MESSAGE;

@WebFilter(
        filterName = "DeletingAttributeCleanerFilter",
        dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST},
        urlPatterns = "/controller"
)
public class DeletingAttributeCleanerFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, @NotNull FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        session.removeAttribute(DELETING_MESSAGE);
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


}
