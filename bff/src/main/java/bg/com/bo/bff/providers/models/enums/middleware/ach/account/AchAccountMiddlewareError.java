package bg.com.bo.bff.providers.models.enums.middleware.ach.account;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchAccountMiddlewareError implements IMiddlewareError {

    MDWAAM_0001(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0001", "La cuenta ya se encuentra registrada.", "Cuenta registrada", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_0002(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWAAM-0002", "La versión es inválida.", "Versión inválida", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_0003(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-0003", "No se pudo realizar el registro. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_0004(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0004", "El tipo de cuenta es inválida.", "Cuenta inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_0005(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0005", "El tipo de oficina es inválido.", "Cuenta inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_0006(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-0006", "La entidad financiera de destino es inválida.", "Cuenta inválida", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_1001(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-1001", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_1002(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-1002", "No se encontraron registros relacionados.", "Datos no encontrados", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_1003(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-1003", "No se encontró una cuenta relacionada a la solicitud.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    MDWHDR_01(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWHDR-01", "Parámetro inválido en la cabecera.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWHDR_02(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWHDR-02", "Canal no implementado.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_001(HttpStatus.CONFLICT, "DATA_NOT_FOUND", "MDWAAM-001", "No se encontraron registros de las sucursales.", "Datos no encontrados", CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_002(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-002", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_003(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-003", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_004(HttpStatus.CONFLICT, "INVALID_DATA", "MDWAAM-004", "No se encontraron cuentas asociadas.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWAAM_005(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-005", "Error en la validación de parámetros.", "Validación de parámetros", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),
    MDWAAM_006(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-006", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_007(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM-007", "Los parámetros deben ser diferentes para el canal 6.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_008(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWAAM-008", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_009(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWAAM-009", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWAAM_010(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWAAM-010", "La versión es inválida.", "Versión inválida", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWAAM_011(HttpStatus.NOT_IMPLEMENTED, "ERROR_PROCEDURE", "MDWAAM-011", "No se pudo procesar el registro de logs.", "Ocurrió un problema", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),

    MDWAAM002(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWAAM002", "Error en la validación de parámetros.", "Validación de parámetros", CategoryError.UNKNOWN_MW_ERROR.getCategoryId()),
    ERROR_ADD_ACCOUNT(HttpStatus.CONFLICT, "INVALID_DATA", null, "Ocurrió un error al agregar la cuenta.", "Error al agregar cuenta", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
