package bg.com.bo.bff.commons.enums.response;

import bg.com.bo.bff.services.implementations.v1.ErrorControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GenericControllerErrorResponse implements ErrorControllerResponse {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.name(), "Not valid credentials."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error."),
    HTTP_CLIENT_CREATION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error."),
    REQUEST_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error."),
    NOT_HANDLED_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), "Internal server error.");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
}

