package bg.com.bo.bff.providers.models.enums.middleware.ach.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchAccountMiddlewareError implements IMiddlewareError {
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "Error en los datos", "Datos inválidos"),
    MDWHDR_01(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-01", "Invalid Header channel", "Parámetros inválidos"),
    MDWHDR_02(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWHDR-02", "Channel not implemented yet", "Canal no implementado"),
    MDWAAM002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM002", "Datos Invalidos", "Datos invalidos"),
    MDWAAM_0001(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-0001", "La cuenta ya se encuentra registrada.", "Cuenta registrada"),
    MDWAAM_0002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-0002", "La versión es inválida.", "Versión inválida"),
    MDWAAM_0003(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0003", "No se encontraron registros asociados a la cuenta.", "Datos inválidos"),
    MDWAAM_0004(HttpStatus.BAD_REQUEST, "NOT_FOUND", "MDWAAM-0004", "El tipo de cuenta es inválida.", "Cuenta inválida"),
    MDWAAM_0005(HttpStatus.BAD_REQUEST, "NOT_FOUND", "MDWAAM-0005", "El tipo de oficina es inválido.", "Cuenta inválida"),
    MDWAAM_0006(HttpStatus.BAD_REQUEST, "NOT_FOUND", "MDWAAM-0006", "La entidad financiera de destino es inválida.", "Cuenta inválida"),
    MDWAAM_001(HttpStatus.BAD_REQUEST, "NOT_FOUND", "MDWAAM-001", "No se encontraron registros.", "Datos no encontrados"),
    MDWAAM_002(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-002", "La cuenta ya se encuentra registrada.", "Cuenta registrada"),
    MDWAAM_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-004", "No tiene datos", "Datos no encontrados"),
    MDWAAM_006(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-006", "Params must be the same for CHANNEL 2", "Error de parámetros"),
    MDWAAM_007(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWAAM-007", "Params must be the same for CHANNEL 6", "Error de parámetros"),
    MDWAAM_010(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-010", "No tiene datos", "Datos no disponibles"),
    MDWAAM_1002(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWAAM-1002", "Datos no encontrados", "Datos no encontrados"),
    MDWAAM_1003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWAAM-1003", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWRLIB_0009(HttpStatus.FORBIDDEN, "FORBIDDEN", "MDWRLIB-0009", "Forbidden", "Acceso denegado"),
    ERROR_ADD_ACCOUNT(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "Ocurrió un error al agregar la cuenta.", "Error al agregar cuenta");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
