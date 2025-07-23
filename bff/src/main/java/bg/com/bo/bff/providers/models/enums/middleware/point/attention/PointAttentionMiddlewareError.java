package bg.com.bo.bff.providers.models.enums.middleware.point.attention;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PointAttentionMiddlewareError implements IMiddlewareError {
    PAM_021(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "PAM-021", "No se encontraron datos"),
    PAM_015(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "PAM-015", "El dato proporcionado es incorrecto");
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
