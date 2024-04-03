package bg.com.bo.bff.commons.enums.response;

import bg.com.bo.bff.services.implementations.v1.ErrorControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RefreshControllerErrorResponse implements ErrorControllerResponse {
    INVALID_DATA(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "Datos inválidos.");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
}
