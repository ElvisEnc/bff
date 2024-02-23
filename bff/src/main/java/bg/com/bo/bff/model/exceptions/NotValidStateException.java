package bg.com.bo.bff.model.exceptions;

public class NotValidStateException extends RuntimeException {
    public NotValidStateException(String message) {
        super(message);
    }
}
