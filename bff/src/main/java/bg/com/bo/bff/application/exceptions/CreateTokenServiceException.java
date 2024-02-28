package bg.com.bo.bff.application.exceptions;

@lombok.Getter
public class CreateTokenServiceException extends RuntimeException {
    private String code;

    public CreateTokenServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
}
