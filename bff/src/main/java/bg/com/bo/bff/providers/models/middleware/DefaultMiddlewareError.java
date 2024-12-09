
package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultMiddlewareError implements IMiddlewareError {
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
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.NO_MAPPED_MW_ERROR.getCategoryId()),
    HEADER_DATA(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "Datos inválidos en la cabecera", "Datos inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),
    EXPIRED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-EAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión expirada", CategoryError.AUTH.getCategoryId()),
    BLACKLISTED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-BAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión inválida", CategoryError.AUTH.getCategoryId()),
    AUTHENTICATION_FILTER_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-AFF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    INVALID_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-IAJ", null, "Por tu seguridad, cerramos la sesión automáticamente.", "Sesión inválida", CategoryError.AUTH.getCategoryId()),
    FORBIDDEN(HttpStatus.FORBIDDEN, "BFF-F", null, "No permitido", "No permitido", CategoryError.AUTH.getCategoryId()),
    KC_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-KCF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId()),
    KC_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "BFF-KCUA", null, "Error interno", "Error interno", CategoryError.BFF_COMPONENT_UNAVAILABLE.getCategoryId()),
    MW_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "BFF-MWSU", null, "Error interno", "Error interno", CategoryError.BFF_COMPONENT_UNAVAILABLE.getCategoryId()),
    MW_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-MWTF", null, "Error interno", "Error interno", CategoryError.UNHANDLED.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
