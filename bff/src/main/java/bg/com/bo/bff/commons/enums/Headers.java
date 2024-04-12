package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Headers {
    SEC("Secret"),
    AUT("Authorization"),
    MW_CHA("middleware-channel"),
    TOP_CHA("topaz-channel"),
    APP_ID("application-id"),
    CONTENT_TYPE("Content-Type"),
    APP_JSON("application/json"),
    DEVICE_ID("device-id"),
    DEVICE_IP("device-ip");

    private final String name;
}
