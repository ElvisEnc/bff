
package bg.com.bo.bff.providers.models.middleware;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum DefaultMiddlewareError implements IMiddlewareError {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "BAD_REQUEST", "Error en los parámetros", "Datos inválidos"),
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "NOT_ACCEPTABLE", "No aceptable", "Datos no aceptables"),
    MDWACM_012(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-012", "Datos inválidos", "Datos inválidos"),
    MDWACM_027(HttpStatus.BAD_REQUEST, "DATA_INVALID", "MDWACM-027", "Error cantidad de limites máximo", "Datos inválidos"),
    INVALID_ENCODE_INFO(HttpStatus.BAD_REQUEST, "BFF-DIEI", null, "Datos inválidos.", "Datos inválidos"),
    NO_ENCODE_INFO(HttpStatus.BAD_REQUEST, "BFF-DNEI", null, "Datos inválidos.", "Datos inválidos"),
    INVALID_ENCRYPTED_DATA(HttpStatus.BAD_REQUEST, "BFF-ICD", null, "Datos inválidos.", "Datos inválidos"),
    INVALID_ENCRYPTION_KEY(HttpStatus.BAD_REQUEST, "BFF-IEK", null, "Datos inválidos.", "Datos inválidos"),
    INVALID_USER_ENCRYPTION_KEY(HttpStatus.BAD_REQUEST, "BFF-IUEK", null, "Datos inválidos.", "Datos inválidos"),
    NOT_AUTHENTICATED_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAU", null, "Usuario no autenticado.", "Usuario no autenticado"),
    NOT_AUTHENTICATED_VALID_USER(HttpStatus.UNAUTHORIZED, "BFF-DNAVU", null, "Usuario autenticado no válido.", "Usuario no válido"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", null, "Error interno", "Error interno"),
    NOT_VALID_DATA(HttpStatus.BAD_REQUEST, "BFF-DNVU", null, "Datos no válidos para el usuario", "Datos inválidos"),
    DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "DEFAULT", "Error interno", "Error interno"),
    HEADER_DATA(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "Datos inválidos en la cabecera", "Datos inválidos"),
    EXPIRED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-EAJ", null, "Sesión expirada", "Por tu seguridad, cerramos la sesión automáticamente."),
    BLACKLISTED_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-BAJ", null, "Token invalidado", "Token inválido"),
    AUTHENTICATION_FILTER_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-AFF", null, "Error interno", "Error interno"),
    INVALID_ACCESS_JWT(HttpStatus.UNAUTHORIZED, "BFF-IAJ", null, "Sesión inválida", "Por tu seguridad, cerramos la sesión automáticamente."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", null, "No permitido", "No permitido"),
    KC_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-KCF", null, "Error interno", "Error interno"),
    MW_TOKEN_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "BFF-MWTF", null, "Error interno", "Error interno");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
