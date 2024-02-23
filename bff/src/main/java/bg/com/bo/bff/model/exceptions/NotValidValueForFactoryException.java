package bg.com.bo.bff.model.exceptions;

public class NotValidValueForFactoryException extends RuntimeException {
    public NotValidValueForFactoryException(String message) {
        super(message);
    }
}
