package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareServices {
    GET_WELCOME_SOFT_TOKEN("/bs/v1/welcome-message-enrollment"),
    GET_DATA_ENROLLMENT_SOFT_TOKEN("/bs/v1/destination-data-enrollment"),
    GET_QUESTION_ENROLLMENT_SOFT_TOKEN("/bs/v1/get-security-question"),
    GET_VALIDATE_ENROLLMENT_SOFT_TOKEN("/bs/v1/validate-enrollment"),
    GET_VALIDATE_CODE_SECURITY_ENROLLMENT_SOFT_TOKEN("/bs/v1/send-security-code-enrollment"),
    POST_VALIDATE_CODE_SECURITY_ENROLLMENT_SOFT_TOKEN("/bs/v1/validate-enrollment-security-code"),
    POST_VALIDATE_QUESTION_SECURITY_ENROLLMENT_SOFT_TOKEN("/bs/v1/validate-question-security-enrollment"),
    GET_PARAMETER_SOFT_TOKEN("/bs/v1/get-token-parameter"),
    POST_REGISTRATION_TOKEN_ENROLLMENT_SOFT_TOKEN("/bs/v1/registration-token"),
    GET_ENROLLMENT_VALIDATE_SOFT_TOKEN("/bs/v1/enrollment-validate"),
    POST_GENERATE_TOKEN_SOFT_TOKEN("/bs/v1/generate-token"),
    POST_ENROLLMENT_SOFT_TOKEN("/bs/v1/register-enrollment"),
    POST_VALIDATE_SOFT_TOKEN("/bs/v1/validate-token");

    private final String serviceURL;
}
