package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Headers {
    SEC("Secret"),
    AUT("Authorization"),
    CHA_MW("middleware-channel"),
    CHA_TOP("topaz-channel"),
    APP_ID("application-id"),
    CONTENT_TYPE("Content-Type"),
    APP_JSON("application/json");

    private final String name;
}
