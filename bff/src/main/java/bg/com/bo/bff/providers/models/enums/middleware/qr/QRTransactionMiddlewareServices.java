package bg.com.bo.bff.providers.models.enums.middleware.qr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QRTransactionMiddlewareServices {
    QR_PAYMENT("/bs/v1/transfer/qr");
    private final String serviceURL;
}
