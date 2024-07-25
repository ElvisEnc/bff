package bg.com.bo.bff.providers.models.enums.middleware.loans;


import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoansMiddlewareError implements IMiddlewareError {
    MDWPRE_001(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-001", "No se encontraron registros"),
    MDWPRE_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-008", "No se encontraron préstamos para el cliente");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
