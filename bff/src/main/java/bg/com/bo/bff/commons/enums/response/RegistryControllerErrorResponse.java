package bg.com.bo.bff.commons.enums.response;

import bg.com.bo.bff.services.implementations.v1.ErrorControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RegistryControllerErrorResponse implements ErrorControllerResponse {
    INVALID_REGISTER(HttpStatus.BAD_REQUEST, "INVALID_REGISTER", "Not enrolled device.");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
}
