package bg.com.bo.bff.providers.models.enums.middleware.payment.services;


import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PaymentServicesMiddlewareError implements IMiddlewareError {
    MDWPSM_001(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPSM-001", "Hubo un problema al consultar los servicios."),
    MDWPSM_003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-003", "No se encontraron registros."),
    MDWPSM_004(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-004", "No se encontraron registros."),
    MDWPSM_005(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-005", "No tiene servicios afiliados"),
    MDWPSM_006(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPSM-006", "Parámetros inválidos."),
    MDWPSM_007(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-007", "No tiene servicios."),
    MDWPSM_010(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPSM-010", "No se encontraron registros."),
    MDWPSM_014(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-014", "No tiene deudas el servicio afiliado"),
    MDWPSM_017(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWPSM-017", "Error al borrar el servicio de afiliación");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
