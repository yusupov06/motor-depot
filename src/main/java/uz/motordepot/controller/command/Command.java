package uz.motordepot.controller.command;

import uz.motordepot.controller.router.Router;
import uz.motordepot.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Command interface
 */
public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;

}
