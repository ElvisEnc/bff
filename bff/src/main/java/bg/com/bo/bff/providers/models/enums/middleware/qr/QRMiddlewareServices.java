package bg.com.bo.bff.providers.models.enums.middleware.qr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QRMiddlewareServices {
    GENERATE("/bs/v1/qrcode/generate"),
    REGENERATE("/bs/v1/qrcode/encrypt"),
    DECRYPT("/bs/v1/qrcode/decrypt");

    private final String serviceURL;
}
