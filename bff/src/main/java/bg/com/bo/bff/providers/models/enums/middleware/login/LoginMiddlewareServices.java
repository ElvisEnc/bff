package bg.com.bo.bff.providers.models.enums.middleware.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginMiddlewareServices {
    VALIDATE_FACTOR("/bs/v1/authentication/validate"),
    VALIDATE_CREDENTIALS("/bs/v1/users/validate-credentials"),
    LOGOUT_SECURELY("/bs/v1/users/log-logout-securely"),
    CHANGE_PASSWORD("/bs/v1/password/change"),
    VALIDATE_ENROLLMENT("/bs/v1/device/validate-enrollment"),
    GET_BIOMETRIC("/bs/v1/users/biometric/%s"),
    UPDATE_BIOMETRIC("/bs/v1/users/biometric/%s"),
    GET_KEYS("/bs/v1/device/get-keys?personId=%s");

    private final String serviceURL;
}
