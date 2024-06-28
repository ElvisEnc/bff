
package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultMiddlewareError implements IMiddlewareError {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parametros"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos Invalidos"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites maximo"),
    INVALID_ENCODE_INFO(HttpStatus.BAD_REQUEST, "BFF-DIEI", null, "Invalid data."),
    NO_ENCODE_INFO(HttpStatus.BAD_REQUEST, "BFF-DNEI", null, "Invalid data."),
    NOT_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAU", null, "Usuario no autenticado."),
    NOT_AUTHENTICATED_VALID_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAVU", null, "Usuario autenticado no válido."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", null, "Error interno"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Error interno");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
