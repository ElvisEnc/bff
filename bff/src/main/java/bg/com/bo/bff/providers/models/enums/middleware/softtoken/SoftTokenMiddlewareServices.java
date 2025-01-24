package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareServices {
    GET_WELCOME_SOFTTOKEN("/bs/v1/welcome-message-enrollment"),
    GET_DATA_ENROLLMENT_SOFTTOKEN("/bs/v1/destination-data-enrollment"),
    GET_QUESTION_ENROLLMENT_SOFTTOKEN("/bs/v1/get-security-question"),
    GET_VALIDATION_ENROLLMENT_SOFTTOKEN("/bs/v1/validate-enrollment"),
    GET_VALIDATION_CODE_SECURITY_ENROLLMENT_SOFTTOKEN("/bs/v1/send-security-code-enrollment");

    private final String serviceURL;
}
