package bg.com.bo.bff.providers.models.enums.middleware.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RegistryControllerErrorResponse implements IErrorControllerResponse {
    INVALID_REGISTER(HttpStatus.BAD_REQUEST, "INVALID_REGISTER", "Not enrolled device."),
    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "ALREADY_REGISTERED", "Device already registered.");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
}
