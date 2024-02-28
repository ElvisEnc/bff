package bg.com.bo.bff.application.exceptions;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
