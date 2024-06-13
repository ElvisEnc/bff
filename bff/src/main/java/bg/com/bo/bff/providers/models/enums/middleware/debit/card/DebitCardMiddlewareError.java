package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareError implements IMiddlewareError {
    MDWTJD_001(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWTJD-001", "No se encontraron registros."),
    MDWTJD_002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWTJD-002", "Parámetros inválidos."),
    MDWTJD_003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTJD-003", "Error interno."),
    MDWTJD_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWTJD-004", "No se encontraron tarjetas de debito"),
    MDWTJD_006(HttpStatus.BAD_REQUEST, "ERROR_BLOCK_CARD", "MDWTJD-006", "No se pudo realizar el cambio de estado de la tarjeta"),
    MDWTJD_900(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTJD-900", "Error interno."),
    MDWTJD_005(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWTJD-005", "No se encontraron autorizaciones de compras por internet."),
    MDWPGL_004(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPGL-004", "Sin datos para mostrar"),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "El CardId o el Código de Persona es inválido");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
