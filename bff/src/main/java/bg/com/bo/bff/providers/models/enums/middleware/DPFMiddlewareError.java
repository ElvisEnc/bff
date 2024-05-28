package bg.com.bo.bff.providers.models.enums.middleware;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DPFMiddlewareError implements IMiddlewareError {
    MDWDPF_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWDPF-002", "No se encontraron registros."),
    MDWRLIB_0013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0013", "Error en los headers. DeviceId");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
