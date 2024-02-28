package bg.com.bo.bff.application.exceptions;

public class NotValidValueForFactoryException extends RuntimeException {
    public NotValidValueForFactoryException(String message) {
        super(message);
    }
}
