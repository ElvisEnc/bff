package bg.com.bo.bff.providers.models.enums.middleware.ach.account;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchAccountMiddlewareError implements IMiddlewareError {

    MDWAAM_0001(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0001", "La cuenta ya se encuentra registrada.", "Cuenta registrada", 1400),
    MDWAAM_0002(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWAAM-0002", "La versión es inválida.", "Versión inválida", 1502),
    MDWAAM_0003(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-0003", "No se pudo realizar el registro. Intentalo más tarde.", "Ocurrió un problema", 1300),
    MDWAAM_0004(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0004", "El tipo de cuenta es inválida.", "Cuenta inválida", 1400),
    MDWAAM_0005(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0005", "El tipo de oficina es inválido.", "Cuenta inválida", 1400),
    MDWAAM_0006(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0006", "La entidad financiera de destino es inválida.", "Cuenta inválida", 1400),
    MDWAAM_1001(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-1001", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", 1502),
    MDWAAM_1002(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-1002", "No se encontraron registros relacionados.", "Datos no encontrados", 1300),
    MDWAAM_1003(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-1003", "No se encontró una cuenta relacionada a la solicitud.", "Datos no encontrados", 1400),

    MDWHDR_01(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWHDR-01", "Parámetro inválido en la cabecera.", "Parámetros inválidos", 1502),
    MDWHDR_02(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWHDR-02", "Canal no implementado.", "Parámetros inválidos", 1502),
    MDWAAM_001(HttpStatus.CONFLICT, "DATA_NOT_FOUND", "MDWAAM-001", "No se encontraron registros de las sucursales.", "Datos no encontrados", 1401),
    MDWAAM_002(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-002", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", 1300),
    MDWAAM_003(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-003", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", 1300),
    MDWAAM_004(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-004", "No se encontraron cuentas asociadas.", "Datos no encontrados", 1400),
    MDWAAM_005(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-005", "Error en la validación de parámetros.", "Validación de parámetros", 1505),
    MDWAAM_006(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-006", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", 1502),
    MDWAAM_007(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-007", "Los parámetros deben ser diferentes para el canal 6.", "Parámetros inválidos", 1502),
    MDWAAM_008(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-008", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", 1300),
    MDWAAM_009(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-009", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", 1300),
    MDWAAM_010(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWAAM-010", "La versión es inválida.", "Versión inválida", 1502),
    MDWAAM_011(HttpStatus.NOT_IMPLEMENTED, "ERROR_PROCEDURE", "MDWAAM-011", "No se pudo procesar el registro de logs.", "Ocurrió un problema", 1505),

    MDWAAM002(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM002", "Error en la validación de parámetros.", "Validación de parámetros", 1505),
    MDWPGL_400(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWPGL-400", "Los parámetros proporcionados no son válidos.", "Parámetros inválidos", 1505),
    MDWPGL_404(HttpStatus.NOT_IMPLEMENTED, "INVALID_REQUEST", "MDWPGL-404", "Petición no permitida, verifica e intenta nuevamente.", "Petición no permitida", 1504),
    MDWPGL_405(HttpStatus.NOT_IMPLEMENTED, "INVALID_METHOD", "MDWPGL-405", "Método no permitido, verifica e intenta nuevamente.", "Método no permitido", 1504),
    MDWPGL_500(HttpStatus.NOT_IMPLEMENTED, "UNKNOWN_ERROR", "MDWPGL-500", "Ocurrió un problema, error interno.", "Servicio no disponible", 1505),
    MDWRLIB_0001(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWRLIB-0001", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", 1502),
    MDWRLIB_0009(HttpStatus.NOT_IMPLEMENTED, "UNKNOWN_ERROR", "MDWRLIB-0009", "Ocurrió un problema, error interno.", "Servicio no disponible", 1505),

    MDWRLIB_0011(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWRLIB-0011", "Parámetro inválido en la cabecera de id aplicación.", "Parámetros inválidos", 1502),
    MDWRLIB_0012(HttpStatus.NOT_IMPLEMENTED, "INVALID_CANAL", "MDWRLIB-0012", "Ocurrió un problema, error interno.", "Servicio no disponible", 1504),

    ERROR_ADD_ACCOUNT(HttpStatus.CONFLICT, "INVALID_DATA", null, "Ocurrió un error al agregar la cuenta.", "Error al agregar cuenta", 1300);

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
