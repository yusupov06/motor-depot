package uz.motordepot.controller.command;

import uz.motordepot.controller.command.auth.LogOutCommand;
import uz.motordepot.controller.command.auth.SignInFinishCommand;
import uz.motordepot.controller.command.auth.SignInGetCommand;
import uz.motordepot.controller.command.auth.SignUpGetCommand;
import uz.motordepot.controller.command.cars.*;
import uz.motordepot.controller.command.cruises.*;
import uz.motordepot.controller.command.driver.*;
import uz.motordepot.controller.command.request.*;


public enum CommandType {

    SIGN_IN(new SignInGetCommand()),
    SIGN_UP(new SignUpGetCommand()),
    FINISH_SIGN_IN(new SignInFinishCommand()),
    LOG_OUT(new LogOutCommand()),

    HOME(new HomeCommand()),
    DEFAULT(new DefaultCommand()),

    REQUESTS(new RequestCommand()),
    ADD_REQUEST(new AddRequestCommand()),
    DELETE_REQUEST(new DeleteRequestCommand()),
    EDIT_REQUEST(new EditRequestCommand()),
    FINISH_EDIT_REQUEST(new FinishEditRequestCommand()),

    CRUISES(new CruisesCommand()),
    ADD_CRUISE_PAGE(new AddCruiseCommand()),
    ADD_CRUISE(new FinishAddCruiseCommand()),
    EDIT_CRUISE(new EditCruiseCommand()),
    EDIT_CRUISE_STATUS(new EditCruiseStatusCommand()),
    FINISH_EDIT_CRUISE(new FinishEditCruiseCommand()),
    DELETE_CRUISE(new DeleteCruiseCommand()),

    CARS(new CarsCommand()),
    ADD_CAR(new AddCarCommand()),
    DELETE_CAR(new DeleteCarCommand()),
    EDIT_CAR(new EditCarCommand()),
    FINISH_EDIT_CAR(new FinishEditCarCommand()),

    DRIVERS(new DriversCommand()),
    ADD_DRIVER(new FinishAddDriverCommand()),
    ADD_DRIVER_PAGE(new AddDriverCommand()),
    DELETE_DRIVER(new DeleteDriverCommand()),
    EDIT_DRIVER_PAGE(new EditDriverCommand()),
    EDIT_DRIVER(new FinishEditDriverCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        for (CommandType value : values()) {
            if (value.name().equals(commandStr.toUpperCase()))
                return value.command;
        }

        return DEFAULT.command;
    }

    public static CommandType defineCommandType(String commandStr) {
        for (CommandType value : values()) {
            if (value.name().equals(commandStr.toUpperCase()))
                return value;
        }

        return DEFAULT;
    }

}
