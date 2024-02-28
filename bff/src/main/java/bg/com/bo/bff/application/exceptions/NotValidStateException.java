package bg.com.bo.bff.application.exceptions;

public class NotValidStateException extends RuntimeException {
    public NotValidStateException(String message) {
        super(message);
    }
}
