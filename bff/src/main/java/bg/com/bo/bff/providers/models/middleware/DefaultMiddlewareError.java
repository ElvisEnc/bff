
package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultMiddlewareError implements IMiddlewareError {
    // Librerias MDW
    MDWPGL_400(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWPGL-400", "Los parámetros proporcionados no son válidos.", "Parámetros inválidos", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWPGL_404(HttpStatus.NOT_IMPLEMENTED, "INVALID_REQUEST", "MDWPGL-404", "Petición no permitida, verifica e intenta nuevamente.", "Petición no permitida", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    MDWPGL_405(HttpStatus.NOT_IMPLEMENTED, "INVALID_METHOD", "MDWPGL-405", "Método no permitido, verifica e intenta nuevamente.", "Método no permitido", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    MDWPGL_500(HttpStatus.NOT_IMPLEMENTED, "UNKNOWN_ERROR", "MDWPGL-500", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),
    MDWPGL_0001(HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_PROCEDURE.getCode(), "MDWPGL-0001", "No se encontraron datos", "Datos no encontrados", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()), // Not data found
    MDWPGL_0002(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWPGL-0002", "Datos inválidos en la petición", ConstantMessages.INVALID_LOGIN_DATA.getTitle(), CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()), // Json invalid
    MDWPGL_003(HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_PROCEDURE.getCode(), "MDWPGL-003", ConstantMessages.GENERIC.getMessage(), ConstantMessages.INVALID_LOGIN_DATA.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()), // Error converting date format
    MDWPGL_004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWPGL-004", "No se encontraron registros.", "Datos no encontrados", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()), // Error data not found
    MDWPGL_900(HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_PROCEDURE.getCode(), "MDWPGL-900", ConstantMessages.GENERIC.getMessage(), "Ocurrio un problema", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()), // No connection to BD

    MDWRLIB_0001(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWRLIB-0001", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWRLIB_0003(HttpStatus.NOT_IMPLEMENTED, "DATA_NOT_FOUND", "MDWRLIB-0003", "Registros no encontrados.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWRLIB_0009(HttpStatus.NOT_IMPLEMENTED, "UNKNOWN_ERROR", "MDWRLIB-0009", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),
    MDWRLIB_0011(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWRLIB-0011", "Parámetro inválido en la cabecera de id aplicación.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWRLIB_0012(HttpStatus.NOT_IMPLEMENTED, "INVALID_CANAL", "MDWRLIB-0012", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),

    MDWRLIB_003(HttpStatus.NOT_IMPLEMENTED, "DATA_NOT_FOUND", "MDWRLIB-003", "Registros no encontrados.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWRLIB_0014(HttpStatus.NOT_IMPLEMENTED, "ACCESS_DENIED", "MDWRLIB-0014", "Error token invalido", "Token Inválido", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    MDWPGL_401(HttpStatus.NOT_IMPLEMENTED, "ACCESS_DENIED", "MDWPGL-401", "Error token expirado", "Access token expired", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),
    MDWRLIB_0007(HttpStatus.NOT_IMPLEMENTED, "INVALID_URL", "MDWRLIB-0007", "Error url inválido", "URL inválido", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parámetros", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "NOT_ACCEPTABLE", "No aceptable", "Datos no aceptables", CategoryError.INVALID_FORMAT.getCategoryId()),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos inválidos", "Datos inválidos", 0),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites máximo", "Datos inválidos", 0),
    INVALID_ENCODED_INFO(HttpStatus.BAD_REQUEST, "BFF-DIEI", null, "Datos inválidos.", "Datos inválidos", CategoryError.ENCRYPTION.getCategoryId()),
    INVALID_ENCRYPT_INFO(HttpStatus.BAD_REQUEST, "BFF-DNEI", null, "Datos inválidos.", "Datos inválidos", CategoryError.ENCRYPTION.getCategoryId()),
    INVALID_ENCRYPTED_DATA(HttpStatus.BAD_REQUEST, "BFF-ICD", null, "Datos inválidos.", "Datos inválidos", CategoryError.ENCRYPTION.getCategoryId()),
    INVALID_ENCRYPTION_KEY(HttpStatus.BAD_REQUEST, "BFF-IEK", null, "Datos inválidos.", "Datos inválidos", CategoryError.ENCRYPTION.getCategoryId()),
    INVALID_USER_ENCRYPTION_KEY(HttpStatus.BAD_REQUEST, "BFF-IUEK", null, "Datos inválidos.", "Datos inválidos", CategoryError.ENCRYPTION.getCategoryId()),
    NOT_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAU", null, "Usuario no autenticado.", "Usuario no autenticado", CategoryError.AUTH.getCategoryId()),
    NOT_AUTHENTICATED_VALID_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAVU", null, "Usuario autenticado no válido.", "Usuario no válido", CategoryError.AUTH.getCategoryId()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    NOT_VALID_DATA(HttpStatus.BAD_REQUEST, "BFF-DNVU", null, "Datos no válidos para el usuario", "Datos inválidos", 0),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Tuvimos un problema interno. Inténtalo nuevamente.", ConstantMessages.GENERIC.getTitle(), CategoryError.NO_MAPPED_MW_ERROR.getCategoryId()),
    HEADER_DATA(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "Datos inválidos en la cabecera", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),
    EXPIRED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-EAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión expirada", CategoryError.AUTH.getCategoryId()),
    BLACKLISTED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-BAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión inválida", CategoryError.AUTH.getCategoryId()),
    AUTHENTICATION_FILTER_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-AFF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    INVALID_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-IAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión inválida", CategoryError.AUTH.getCategoryId()),
    FORBIDDEN(HttpStatus.FORBIDDEN, "BFF-F", null, "No permitido", "No permitido", CategoryError.AUTH.getCategoryId()),
    KC_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-KCF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    KC_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "BFF-KCUA", null, "Error interno", "Error interno", CategoryError.BFF_COMPONENT_UNAVAILABLE.getCategoryId()),
    MW_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "BFF-MWSU", null, "Servicio no disponible, inténtalo más tarde.", "Servicio no disponible", CategoryError.BFF_COMPONENT_UNAVAILABLE.getCategoryId()),
    MW_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-MWTF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
