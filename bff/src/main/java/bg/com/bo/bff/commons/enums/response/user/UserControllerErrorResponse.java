package bg.com.bo.bff.commons.enums.response.user;

import bg.com.bo.bff.commons.enums.response.IErrorControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserControllerErrorResponse implements IErrorControllerResponse {
    INVALID_DATA(HttpStatus.BAD_REQUEST, "INVALID_DATA", "Datos inválidos.");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
}
