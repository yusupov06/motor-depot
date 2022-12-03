package uz.motordepot.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.controller.navigation.PageNavigation;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.SESSION_ATTRIBUTE_CURRENT_PAGE;

@WebListener
public class SessionCreateListenerImpl implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        session.setAttribute(SESSION_ATTRIBUTE_CURRENT_PAGE, PageNavigation.DEFAULT);
        logger.info("Session created: {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Session destroyed: {}", se.getSession().getId());
    }
}
