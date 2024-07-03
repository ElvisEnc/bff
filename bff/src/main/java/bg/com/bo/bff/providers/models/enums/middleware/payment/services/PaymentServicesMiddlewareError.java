package bg.com.bo.bff.providers.models.enums.middleware.payment.services;


import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareError implements IMiddlewareError {
    MDWPSM_003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-003", "No se encontraron registros."),
    MDWPSM_004(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-004", "No se encontraron registros."),
    MDWPSM_005(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-005", "No tiene servicios afiliados"),
    MDWPSM_007(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-007", "No tiene servicios."),
    MDWPSM_017(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-017", "Error al borrar el servicio de afiliación");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
