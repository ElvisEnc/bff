package bg.com.bo.bff.providers.models.enums.middleware.qr;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QRMiddlewareError implements IMiddlewareError {
    QR_EXPIRED(HttpStatus.BAD_REQUEST, "QR_EXPIRED", "QR_EXPIRED", "El código QR ha expirado.");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
