package bg.com.bo.bff.model.exceptions;

public class NotHandledResponseException extends RuntimeException {

    public NotHandledResponseException() {
        super("Respuesta no controlada.");
    }

    public NotHandledResponseException(String message) {
        super(message);
    }
}
