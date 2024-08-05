package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareError implements IMiddlewareError {
    MDWACM_002(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-002", "Sin registros"),
    MDWACM_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-008", "Sin registros"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos Invalidos"),
    MDWACM_013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACM-013", "La cuenta no existe"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites maximo");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
