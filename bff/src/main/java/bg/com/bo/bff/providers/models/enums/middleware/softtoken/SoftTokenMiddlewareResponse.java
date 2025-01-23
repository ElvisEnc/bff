package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareResponse {
    ENROLLMENT("PINDIG002", "ENROLLMENT"),
    NOT_ENROLLMENT("PINDIG003", "NOT_ENROLLMENT");

    private final String code;
    private final String message;
}