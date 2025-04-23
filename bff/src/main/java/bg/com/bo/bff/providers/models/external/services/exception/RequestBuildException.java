package bg.com.bo.bff.providers.models.external.services.exception;

public class RequestBuildException extends Exception {
    public RequestBuildException(String message) {
        super(message);
    }

    public RequestBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}