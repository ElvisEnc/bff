package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DebitCardMiddlewareError implements IMiddlewareError {
    MDWTJD_001(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWTJD-001", "No se encontraron registros."),
    MDWTJD_002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWTJD-002", "Parámetros inválidos."),
    MDWTJD_003(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWTJD-003", "La tarjeta se encuentra bloqueada."),
    MDWTJD_900(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTJD-900", "Error interno.");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
