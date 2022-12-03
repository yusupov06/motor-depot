package uz.motordepot.exception;

import lombok.Getter;


@Getter
public class ServiceException extends RuntimeException {

    private final String message;
    private final int status;

    private ServiceException(String message, int status) {
        this.status = status;
        this.message = message;
    }

    public static ServiceException throwExc(String message, int status) {
        return new ServiceException(message, status);
    }

    public static ServiceException throwExc(String message) {
        return new ServiceException(message, 401);
    }
}
