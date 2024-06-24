package bg.com.bo.bff.providers.models.enums.middleware.payment.services;


import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareError implements IMiddlewareError {
    MDWPSM_003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-003", "No se encontraron registros.");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
