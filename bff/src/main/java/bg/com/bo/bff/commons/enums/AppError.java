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
    MDWLM_012(HttpStatus.NOT_ACCEPTABLE,"NOT_ACCEPTABLE","MDWLM-012","Error en el procesamiento de parametros"),
    MDWLM_018(HttpStatus.UNAUTHORIZED,"EXPIRED_PASSWORD_LM","MDWLM-018","The password has expired, you must change it"),
    MDWLM_019(HttpStatus.UNAUTHORIZED,"EXPIRED_PASSWORD_DAYS_LM","MDWLM-019","The password will expire in X days"),
    MDWLM_020(HttpStatus.UNAUTHORIZED,"PASSWORD_CHANGE_LM","MDWLM-020","You must change the password"),
    MDWLM_22(HttpStatus.NOT_ACCEPTABLE,"INVALID_DATA","MDWLM-22","Estimado cliente, sus datos son incorrectos"),
    MDWLM_23(HttpStatus.NOT_ACCEPTABLE,"BLOCKED_USER","MDWLM-23","Estimado cliente, has superado los intentos máximos, necesitamos validar su identidad"),
    MDWLM_24(HttpStatus.NOT_ACCEPTABLE,"NOT_ENROLLED","MDWLM-24","Estimado cliente, está intentando iniciar sesión en un nuevo dispositivo, necesitamos validar su identidad"),
    MDWLM_25(HttpStatus.NOT_ACCEPTABLE,"RESTRICTED_USER","MDWLM-25","Estimado cliente, necesitamos por favor que visite nuestras oficinas para validar su identidad"),

    MDWRLIB_0003(HttpStatus.NOT_ACCEPTABLE,"NOT_ENROLLED","MDWRLIB-0003","Dispositivo no enrolado"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR,"ERROR","DEFAULT","This is a default error");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
    private final String message;
}
