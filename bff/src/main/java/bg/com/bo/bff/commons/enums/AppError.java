package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum AppError {
    MDWLM_009(HttpStatus.UNAUTHORIZED,"ERROR_INTERN","MDWLM-009","User not found"),
    MDWLM_010(HttpStatus.UNAUTHORIZED,"PERSON_NOT_FOUND","MDWLM-010","Device or Person not enrolled"),
    MDWLM_011(HttpStatus.UNAUTHORIZED,"DATA_INVALID","MDWLM-011","Invalid data"),
    MDWLM_012(HttpStatus.NOT_ACCEPTABLE,"PASSWORD_ATTEMPTS_LM","MDWLM-012","error in processing"),
    MDWLM_018(HttpStatus.UNAUTHORIZED,"EXPIRED_PASSWORD_LM","MDWLM-018","The password has expired, you must change it"),
    MDWLM_019(HttpStatus.UNAUTHORIZED,"EXPIRED_PASSWORD_DAYS_LM","MDWLM-019","The password will expire in X days"),
    MDWLM_020(HttpStatus.UNAUTHORIZED,"PASSWORD_CHANGE_LM","MDWLM-020","You must change the password"),
    MDWLM_22(HttpStatus.NOT_ACCEPTABLE,"ERROR_PROCEDURE","MDWLM-22","You have exceeded the maximum attempts, we need your identity validation"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR,"ERROR","DEFAULT","This is a default error in the BFF");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
    private final String message;
}
