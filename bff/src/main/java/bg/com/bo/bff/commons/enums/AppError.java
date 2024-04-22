package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppError {
    // Login
    MDWLM_009(HttpStatus.UNAUTHORIZED, "ERROR_INTERN", "MDWLM-009", "User not found"),
    MDWLM_010(HttpStatus.UNAUTHORIZED, "PERSON_NOT_FOUND", "MDWLM-010", "Device or Person not enrolled"),
    MDWLM_011(HttpStatus.UNAUTHORIZED, "DATA_INVALID", "MDWLM-011", "Invalid data"),
    MDWLM_012(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-012", "Estimado cliente, sus datos son incorrectos"),
    MDWLM_013(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLM-013", "Error en el procesamiento de parametros"),
    MDWLM_018(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_LM", "MDWLM-018", "The password has expired, you must change it"),
    MDWLM_019(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_DAYS_LM", "MDWLM-019", "The password will expire in X days"),
    MDWLM_020(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-020", "You must change the password"),
    MDWLM_23(HttpStatus.UNAUTHORIZED, "BLOCKED_USER", "MDWLM-23", "Estimado cliente, has superado los intentos máximos, necesitamos validar su identidad"),
    MDWLM_24(HttpStatus.UNAUTHORIZED, "NOT_ENROLLED", "MDWLM-24", "Estimado cliente, está intentando iniciar sesión en un nuevo dispositivo, necesitamos validar su identidad"),
    MDWLM_25(HttpStatus.UNAUTHORIZED, "RESTRICTED_USER", "MDWLM-25", "Estimado cliente, necesitamos por favor que visite nuestras oficinas para validar su identidad"),

    MDWRLIB_0003(HttpStatus.NOT_ACCEPTABLE, "NOT_ENROLLED", "MDWRLIB-0003", "Dispositivo no enrolado"),

    // Logout
    MDWRLIB_0001(HttpStatus.BAD_REQUEST, "NOT_ENROLLED", "MDWRLIB-0001", "Error en los headers. Channel"),
    MDWRLIB_0011(HttpStatus.BAD_REQUEST, "NOT_ENROLLED", "MDWRLIB-0011", "Error en los headers. ApplicationId"),
    MDWPGL_404(HttpStatus.NOT_FOUND, "NOT_ENROLLED", "MDWPGL-404", "Not found"),
    MDWPGL_405(HttpStatus.METHOD_NOT_ALLOWED, "NOT_ENROLLED", "MDWPGL-405", "Method not allowed"),
    MDWRLIB_003(HttpStatus.NOT_ACCEPTABLE, "NOT_ENROLLED", "MDWRLIB-003", "Error en los datos"),
    KEY_CLOAK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "KEY_CLOAK_ERROR", "Error interno."),

    // Extractos
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "Error en los DATOS"),
    MDWACM_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWACM-008", "Sin registros"),
    MDWACM_013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACM-013", "La cuenta no existe"),

    // Agendar Cuentas
    MDWRACTM_004(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWRACTM-004", "Datos Invalidos"),
    MDWAAM_002(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWAAM-002", "Datos Invalidos"),

    // Genericos
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parametros"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Error interno");

    private final HttpStatus httpCode;
    private final String code;
    private final String description;
    private final String message;

    public static AppError findByCode(String description) {

        for (AppError constant : AppError.values()) {
            if (constant.getDescription().equals(description)) {
                return constant;
            }
        }
        return AppError.DEFAULT;
    }
}
