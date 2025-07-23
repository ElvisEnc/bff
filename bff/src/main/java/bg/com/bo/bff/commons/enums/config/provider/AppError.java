package bg.com.bo.bff.commons.enums.config.provider;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AppError implements IMiddlewareError {
    // Login
    PGL_401(HttpStatus.UNAUTHORIZED, "BFF-MWTF", "PGL-401", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWLM_010(HttpStatus.UNAUTHORIZED, "PERSON_NOT_FOUND", "MDWLM-010", "Verifica tu código de persona o contraseña.", "Datos inválidos"),
    MDWLM_011(HttpStatus.UNAUTHORIZED, "DATA_INVALID", "MDWLM-011", "Verifica tu código de persona y contraseña.", "Datos inválidos"),
    MDWLM_012(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-012", "Verifica tu código de persona y contraseña.", "Datos inválidos"),
    MDWLM_013(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLM-013", "Error en el procesamiento de parametros.", "Error en los parametros"),
    MDWLM_018(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_LM", "MDWLM-018", "La contraseña ha expirado.", "Contraseña expirada"),
    MDWLM_019(HttpStatus.UNAUTHORIZED, "EXPIRED_PASSWORD_DAYS_LM", "MDWLM-019", "La contraseña expirará muy pronto.", "Contraseña por expirar"),
    MDWLM_020(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-020", "Estimado cliente, debe cambiar la contraseña.", "Cambio de contraseña"),
    MDWLM_23(HttpStatus.UNAUTHORIZED, "BLOCKED_USER", "MDWLM-23", "Has superado el número máximo de intentos. Por tu seguridad, necesitamos verificar tu identidad.", "Usuario bloqueado"),
    MDWLM_24(HttpStatus.UNAUTHORIZED, "NOT_ENROLLED", "MDWLM-24", "¿Deseas habilitar GanaMóvil en este dispositivo?", "Habilitación de GanaMóvil"),
    MDWLM_25(HttpStatus.UNAUTHORIZED, "RESTRICTED_USER", "MDWLM-25", "Visita una agencia para confirmar tu identidad.", "Acceso restringido"),

    MDWLM_008(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-008", "Verifica tu método de ingreso y contraseña.", "Datos inválidos"), // alias, DNI
    MDWLM_009(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-009", "Verifica tu código de persona y contraseña.", "Datos inválidos"), // personId
    MDWLM_007(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-007", "Tipo de autenticación inválido.", "Autenticación inválida"), // type
    MDWLM_12(HttpStatus.UNAUTHORIZED, "INVALID_DATA", "MDWLM-12", "Verifica tu código de persona y contraseña", "Datos inválidos"), // contraseña
    MDWLM_021(HttpStatus.UNAUTHORIZED, "PASSWORD_CHANGE_LM", "MDWLM-021", "Estimado cliente, debe cambiar la contraseña", "Cambio de contraseña"), // contraseña
    MDWRLIB_0003(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWRLIB-0003", "Error en los datos.", "Datos no encontrados"),
    MDWLM_29(HttpStatus.INTERNAL_SERVER_ERROR, "DUPLICATED_DEVICE_ID", "MDWLM-29", "No se puede validar el device id.", "Device Id duplicado"),

    // Logout
    MDWRLIB_0001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0001", "Error en los headers. Channel", "Datos inválidos"),
    MDWRLIB_0011(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0011", "Error en los headers. ApplicationId", "Datos inválidos"),
    MDWPGL_404(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPGL-404", "No encontrado", "Datos incorrectos"),
    MDWPGL_405(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "MDWPGL-405", "Método no permitido", "Datos inválidos"),
    MDWRLIB_003(HttpStatus.NOT_ACCEPTABLE, "BAD_REQUEST", "MDWRLIB-003", "Error en los datos otorgados", "Datos inválidos"),
    KEY_CLOAK_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR_KC", "KEY_CLOAK_ERROR", "Error interno.", "Error interno"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_TOKEN", "INVALID_TOKEN", "El token es inválido", "Token inválido"),

    // Extractos
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "Error en los datos", "Datos inválidos"),

    // Agendar Cuentas
    MDWRACTM_004(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWRACTM-004", "Datos Invalidos", "Datos inválidos"),
    MDWAAM_002(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWAAM-002", "Datos Invalidos", "Datos inválidos"),

    // Cuentas Terceros
    MDWRLIB_0012(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0012", "Error en el canal. Canal no permitido", "Canal no permitido"),

    //DPFs
    MDWDPF_002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWDPF-002", "No se encontraron registros.", "Datos no encontrados"),
    MDWRLIB_0013(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWRLIB-0013", "Error en los headers. DeviceId", "Datos inválidos"),

    // Genericos
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parametros", "Datos inválidos"),

    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),

    //Usuarios
    NOT_ACCEPTABLE_UPDATE_PERSONAL_INFORMATION(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "NOT_ACCEPTABLE", "Los datos solo se pueden actulizar unsa sola vez por día", "Datos inválidos"),
    VALIDATE_MARRIED_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "EL nombre del conyuge no puede estar vacio", "Datos inválidos"),
    VALIDATE_MARRIED_AND_USE_HUSBAND_LAST_NAME_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El apellido del conyuge no puede estar vacio", "Datos inválidos"),
    VALIDATE_USE_HUSBAND_LAST_NAME_PERSON(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El campo usa apellido del conyuge no puede estar vacio", "Datos inválidos"),
    REFERENCE_INVALID(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Los datos de la referencia estan incompletos", "Datos inválidos"),
    INCOME_LEVEL_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El código de nivel de ingreso no existe", "Datos inválidos"),
    INCOME_SOURCE_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El código de fuente de ingreso no existe", "Datos inválidos"),
    POSITION_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El código de cargo no existe", "Datos inválidos"),
    ECONOMIC_ACTIVITY_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El código de actividad económica no existe", "Datos inválidos"),
    COMPANY_NAME_NOT_NULL(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El nombre de la compañia no debe ser nulo", "Datos inválidos"),
    CITY_CODE_NOT_EXIST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "El código de ciudad no existe", "Datos inválidos");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;

    public static AppError findByCode(String description) {

        for (AppError constant : AppError.values()) {
            if (constant.getCodeMiddleware().equals(description)) {
                return constant;
            }
        }
        return AppError.DEFAULT;
    }
}
