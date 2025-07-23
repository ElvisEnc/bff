package bg.com.bo.bff.commons.enums.config.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceMW {
    MIDDLEWARE_CHANNEL("middleware-channel"),
    APPLICATION_ID("application-id"),
    DEVICE_ID("device-id"),
    DEVICE_IP("device-ip"),
    DEVICE_NAME("device-name"),
    GEO_POSITION_X("geo-position-x"),
    GEO_POSITION_Y("geo-position-y"),
    APP_VERSION("app-version"),
    JSON_DATA("json-data"),
    USER_DEVICE_ID("user-device-id");
    private final String code;

}
