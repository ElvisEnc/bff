package bg.com.bo.bff.providers.models.enums.middleware.loans;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoansMiddlewareError implements IMiddlewareError {
    MDWPRE_001(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-001", "No se encontraron registros de pagos"),
    MDWPRE_004(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-004", "No se encontraron registros de pagos de seguro"),
    MDWPRE_006(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-006", "No se encontraron registros"),
    MDWPRE_007(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DB_EXECUTION", "MDWPRE-007", "Ocurrio un error al obtener datos."),
    MDWPRE_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-008", "No se encontraron préstamos para el cliente"),
    MDWPRE_900(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DB_EXECUTION", "MDWPRE-900", "Ocurrio un error al obtener datos."),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "El número de préstamo no se encontró en los registros");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
