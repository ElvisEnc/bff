package bg.com.bo.bff.providers.models.enums.middleware.login;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoginMiddlewareError implements IMiddlewareError {
    // Login
    MDWLM_010(HttpStatus.UNAUTHORIZED, "PERSON_NOT_FOUND", "MDWLM-010", "Verifica tu código de persona o contraseña.", "Datos inválidos"),
    MDWLM_011(HttpStatus.UNAUTHORIZED, "DATA_INVALID", "MDWLM-011", "Verifica tu código de persona y contraseña.", "Datos inválidos"),
    MDWLM_012(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-012", "Verifica tu código de persona y contraseña.", "Datos inválidos"),
    MDWLM_013(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLM-013", "Error en el procesamiento de parametros.", "Error en los parametros"),
    MDWLM_018(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_LM", "MDWLM-018", "La contraseña ha expirado.", "Contraseña expirada"),
    MDWLM_019(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_DAYS_LM", "MDWLM-019", "La contraseña expirará muy pronto.", "Contraseña por expirar"),
    MDWLM_020(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-020", "Estimado cliente, debe cambiar la contraseña.", "Cambio de contraseña"),
    MDWLM_23(HttpStatus.UNAUTHORIZED, "BLOCKED_USER", "MDWLM-23", "Has superado el número máximo de intentos. Por tu seguridad, necesitamos verificar tu identidad.", "Usuario bloqueado"),
    MDWLM_24(HttpStatus.UNAUTHORIZED, "NOT_ENROLLED", "MDWLM-24", "¿Deseas habilitar GanaMóvil en este dispositivo?", "Habilitación de GanaMóvil"),
    MDWLM_25(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "MDWLM-25", "Verifica tus datos o visita una agencia para confirmar tu identidad.", "Acceso denegado"),
    MDWLM_008(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-008", "Verifica tu método de ingreso y contraseña.", "Datos inválidos"), // alias, DNI
    MDWLM_009(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-009", "Verifica tu código de persona y contraseña.", "Datos inválidos"), // personId
    MDWLM_007(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-007", "Tipo de autenticación inválido.", "Autenticación inválida"), // type
    MDWLM_12(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-12", "Verifica tu código de persona y contraseña", "Datos inválidos"), // contraseña
    MDWLM_021(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-021", "Estimado cliente, debe cambiar la contraseña", "Cambio de contraseña"), // contraseña
    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWRLIB-0003", "Error en los datos.", "Datos no encontrados"),
    MDWLM_29(HttpStatus.INTERNAL_SERVER_ERROR, "DUPLICATED_DEVICE_ID", "MDWLM-29", "No se puede validar el device id.", "Device Id duplicado"),
    MDWLM_31(HttpStatus.CONFLICT, "BIOMETRIC_DISABLED", "MDWLM-31", "Estimado cliente, no tienes habilitado el ingreso por biometría.", "Biometría no habilitada"),
    INVALID_AUTH_TYPE(HttpStatus.BAD_REQUEST, "INVALID_DATA", null, "Tipo de autenticación inválida.", "Datos inválidos"),

    // Logout
    MDWRLIB_0001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0001", "Error en los headers. Channel", "Datos inválidos"),
    MDWRLIB_0011(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0011", "Error en los headers. ApplicationId", "Datos inválidos"),
    MDWPGL_405(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "MDWPGL-405", "Método no permitido", "Datos inválidos"),
    MDWRLIB_003(HttpStatus.NOT_ACCEPTABLE, "BAD_REQUEST", "MDWRLIB-003", "Error en los datos otorgados", "Datos inválidos"),
    KEY_CLOAK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR_KC", "KEY_CLOAK_ERROR", "Error interno.", "Error interno"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "INVALID_TOKEN", "El token es inválido", "Token inválido"),
    BIOMETRIC_TOKEN(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "El token biométrico no debe estar en vació.", "Datos inválidos"),
    PASSWORD_BLANK(HttpStatus.CONFLICT, "INVALID_DATA", null, "La contraseña no debe estar en vació.", "Datos inválidos"),
    ERROR_CHANGE_PASSWORD(HttpStatus.CONFLICT, "INVALID_DATA", null, "La contraseña no debe estar en vació.", "Datos inválidos"),

    // Change password
    MDWPWD7777(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPGL_500(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-500", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Los datos proporcionados son inválidos. Verifica e intenta nuevamente.", "Datos inválidos"),
    MDWPGL_404(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPGL-404", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWRLIB_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0009", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWRLIB_0012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWRLIB-0012", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    WDPWD_0002(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPWD-0002", "Los datos proporcionados no son válidos. Verifica e intenta nuevamente.", "Datos inválidos"),
    MDWPWD_0005(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0005", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPWD_0006(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0006", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    SAME_PASSWORD(HttpStatus.CONFLICT, "SAME_PASSWORD", "MDWPWD-0007", "La nueva contraseña no puede ser igual a la anterior. Elige una diferente.", "Contraseña repetida"),
    MDWPWD_0099(HttpStatus.CONFLICT, "REPEATED_PASSWORD", "MDWPWD-0099", "La contraseña ingresada ya ha sido utilizada. Elige una nueva contraseña.", "Contraseña repetida"),
    MDWPWD_0008(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0008", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPWD_0009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0009", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPWD_0010(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWPWD-0010", "Ha ocurrido un error interno en el servidor. Inténtalo de nuevo más tarde.", "Error interno"),
    MDWPWDSEG_004(HttpStatus.UNAUTHORIZED, "INVALID_PASSWORD", "MDWPWD-SEG004", "La contraseña ingresada es incorrecta. Verifique e intente nuevamente.", "Contraseña inválida"),
    MDWPWD_2001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-2001", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
    MDWPWD_9001(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-9001", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
    MDWPWD_0011(HttpStatus.BAD_REQUEST, "INVALID_USER", "MDWPWD-0011", "El usuario proporcionado no es válido. Verifica los datos e inténtalo nuevamente.", "Usuario inválido"),
    MDWPWDSEG_014(HttpStatus.UNAUTHORIZED, "MAX_ATTEMPS", "MDWPWD-SEG014", "Se ha alcanzado el número máximo de intentos permitidos. Inténtalo nuevamente más tarde.", "Intentos máximos alcanzados"),
    NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "NOT_VALID_PASSWORD", null, "La nueva contraseña no cumple con los requisitos. Verifica e intenta nuevamente.", "Contraseña inválida");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
