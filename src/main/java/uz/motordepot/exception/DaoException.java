package uz.motordepot.exception;

public class DaoException extends RuntimeException {
    public DaoException(String error) {
        super(error);
    }
}
