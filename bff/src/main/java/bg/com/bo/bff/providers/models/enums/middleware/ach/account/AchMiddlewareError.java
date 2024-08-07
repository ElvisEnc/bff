package bg.com.bo.bff.providers.models.enums.middleware.ach.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchMiddlewareError implements IMiddlewareError {
    MDWHDR_01(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-01", "Invalid Header channel"),
    MDWHDR_02(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-02", "Channel not implemented yet"),
    MDWAAM002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM002", "Datos Invalidos"),
    MDWAAM_001(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWAAM-001", "No tiene datos"),
    MDWAAM_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-004", "No tiene datos"),
    MDWAAM_006(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-006", "Params must be the same for CHANNEL 2"),
    MDWAAM_010(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-010", "No tiene datos"),
    MDWRLIB_0009(HttpStatus.FORBIDDEN, "FORBIDDEN", "MDWRLIB-0009", "Forbidden");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
