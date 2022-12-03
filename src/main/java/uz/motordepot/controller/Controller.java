package uz.motordepot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.motordepot.controller.command.Command;
import uz.motordepot.controller.command.CommandType;
import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static uz.motordepot.controller.navigation.AttributeParameterHolder.PARAMETER_COMMAND;

/**
 * Me: muhammadqodir
 * Project: motor-depot2/IntelliJ IDEA
 * Date:Sun 13/11/22 11:53
 */

@WebServlet(name = "Controller", value = "/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    @Override
    public void init() throws ServletException {
        logger.info("SERVLET INIT");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);

        String commandStr = request.getParameter(PARAMETER_COMMAND);

        Command command = CommandType.define(commandStr);

        try {
            Router router = command.execute(request);
            String page = router.getPage();
            switch (router.getType()) {
                case FORWARD -> request.getRequestDispatcher(page).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + page);
                default -> {
                    logger.error(" Error while routing type");
                    response.sendError(500);
                }
            }
        } catch (CommandException e) {
            logger.error("Error while command executes");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void destroy() {
        logger.info("SERVLET DESTROY");
    }
}
