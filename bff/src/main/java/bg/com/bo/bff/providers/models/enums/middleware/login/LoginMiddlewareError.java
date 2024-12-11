package bg.com.bo.bff.providers.models.enums.middleware.login;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoginMiddlewareError implements IMiddlewareError {
    // Login
    MDWLM_010(HttpStatus.UNAUTHORIZED, "PERSON_NOT_FOUND", "MDWLM-010", "Verifica tu código de persona y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()),
    MDWLM_011(HttpStatus.UNAUTHORIZED, "DATA_INVALID", "MDWLM-011", "Verifica tu código de persona y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()),
    MDWLM_012(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-012", "Verifica tu código de persona y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()),
    MDWLM_013(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWLM-013", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLM_23(HttpStatus.UNAUTHORIZED, "BLOCKED_USER", "MDWLM-23", "Has superado el número máximo de intentos. Por tu seguridad, necesitamos verificar tu identidad.", "Usuario bloqueado", CategoryError.AUTH.getCategoryId()),
    MDWLM_24(HttpStatus.UNAUTHORIZED, "NOT_ENROLLED", "MDWLM-24", "¿Deseas habilitar GanaMóvil en este dispositivo?", "Habilitación de GanaMóvil", CategoryError.AUTH.getCategoryId()),
    MDWLM_25(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "MDWLM-25", "Verifica tus datos o visita una agencia para confirmar tu identidad.", "Acceso denegado", CategoryError.AUTH.getCategoryId()),
    MDWLM_008(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-008", "Verifica tu método de ingreso y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()), // alias, DNI
    MDWLM_009(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-009", "Verifica tu código de persona y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()), // personId
    MDWLM_007(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-007", "Tipo de autenticación inválido.", "Autenticación inválida", CategoryError.AUTH.getCategoryId()), // type
    MDWLM_12(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-12", "Verifica tu código de persona y contraseña", "Datos inválidos", CategoryError.AUTH.getCategoryId()), // contraseña
    MDWLM_021(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-021", "Estimado cliente, debe cambiar la contraseña", "Cambio de contraseña", CategoryError.AUTH.getCategoryId()), // contraseña

    MDWLM_29(HttpStatus.NOT_ACCEPTABLE, "DUPLICATED_DEVICE_ID", "MDWLM-29", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLM_31(HttpStatus.CONFLICT, "BIOMETRIC_DISABLED", "MDWLM-31", "Estimado cliente, no tienes habilitado el ingreso por biometría.", "Biometría no habilitada", CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()),
    INVALID_AUTH_TYPE(HttpStatus.BAD_REQUEST, "INVALID_DATA", null, "Tipo de autenticación inválida.", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),

    // Logout
    KEY_CLOAK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR_KC", "KEY_CLOAK_ERROR", "Error interno.", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    BIOMETRIC_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "El token biométrico no debe estar en vació.", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),
    PASSWORD_BLANK(HttpStatus.BAD_REQUEST, "INVALID_DATA", null, "La contraseña no debe estar en vació.", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),

    // Change password
    WDPWD_0002(HttpStatus.NOT_ACCEPTABLE, "INVALID_DATA", "MDWPWD-0002", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPWD_0005(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWPWD-0005", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPWD_0006(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWPWD-0006", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    SAME_PASSWORD(HttpStatus.CONFLICT, "SAME_PASSWORD", "MDWPWD-0007", "La nueva contraseña no puede ser igual a la anterior. Elige una diferente.", "Contraseña inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPWD_0099(HttpStatus.CONFLICT, "REPEATED_PASSWORD", "MDWPWD-0099", "La contraseña ingresada ya ha sido utilizada. Elige una nueva contraseña.", "Contraseña inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPWD_0008(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWPWD-0008", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPWD_0009(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWPWD-0009", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPWD_0010(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWPWD-0010", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPWDSEG_004(HttpStatus.CONFLICT, "INVALID_PASSWORD", "MDWPWD-SEG004", "La contraseña ingresada es incorrecta. Verifique e intente nuevamente.", "Contraseña inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    MDWPWD_2001(HttpStatus.CONFLICT, "INVALID_USER", "MDWPWD-2001", "No se pudo validar los datos, acercate a una agencia cercana.", "Datos inválidos", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPWD_9001(HttpStatus.CONFLICT, "INVALID_USER", "MDWPWD-9001", "No se pudo validar los datos, acercate a una agencia cercana.", "Datos inválidos", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPWD_0011(HttpStatus.CONFLICT, "INVALID_USER", "MDWPWD-0011", "No se pudo validar los datos, acercate a una agencia cercana.", "Datos inválidos", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPWDSEG_014(HttpStatus.CONFLICT, "MAX_ATTEMPS", "MDWPWD-SEG014", "Se ha alcanzado el número máximo de intentos permitidos. Inténtalo nuevamente más tarde.", "Intentos máximos alcanzados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    NOT_VALID_PASSWORD(HttpStatus.CONFLICT, "NOT_VALID_PASSWORD", null, "La nueva contraseña no cumple con los requisitos. Verifica e intenta nuevamente.", "Contraseña inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    //added
    MDWLM_006(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-006", "Verifica tus datos o visita una agencia para confirmar tu identidad.", "Datos inválidos", CategoryError.AUTH.getCategoryId()),
    MDWLM_014(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-014", "Verifica tu código de persona y contraseña.", "Datos inválidos", CategoryError.AUTH.getCategoryId()),
    MDWLM_26(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWLM-26", "La contraseña no debe estar en vació.", "Datos inválidos", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWLM_27(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWLM-27", "Token no debe estar en vació.", "Datos inválidos", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWLM_28(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-28", "No se pudo validar tu identidad, acercate a una agencia cercana.", "Ocurrió un problema", CategoryError.AUTH.getCategoryId()),
    MDWLM_30(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWLM-30", "Error al realizar la verificacion del registro.", "Error verificación", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),
    MDWPWD_7777(HttpStatus.NOT_IMPLEMENTED, "INVALID", "MDWPWD-7777", "Excepcion inesperada.", "Excepcion inesperada", CategoryError.UNKNOWN_MW_ERROR.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
