package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareError implements IMiddlewareError {
    MDWACM_002(HttpStatus.CONFLICT, "DATA_NOT_FOUND", "MDWACM-002", "No se encontraron registros.", "Datos no encontrados", CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_008(HttpStatus.CONFLICT, "DATA_NOT_FOUND", "MDWACM-008", "No se encontraron registros de los extractos.", "Datos no encontrados", CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_012(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACM-012", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_013(HttpStatus.NOT_ACCEPTABLE, "ERROR_DB_PROCEDURE", "MDWACM-013", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_028(HttpStatus.NOT_IMPLEMENTED, "ERROR_LOG", "MDWACM-028", "El registro de Log no puede ser procesado", "Error log", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_009(HttpStatus.CONFLICT, "DATA_NOT_FOUND", "MDWACM-009", "No se encontraron registros.", "Datos no encontrados", CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_010(HttpStatus.NOT_ACCEPTABLE, "ERROR_PROCEDURE", "MDWACM-010", "Tuvimos un problema interno. Intentalo más tarde.", "Ocurrió un problema", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_019(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWACM-019", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACM_020(HttpStatus.NOT_IMPLEMENTED, "INVALID_PARAMS", "MDWACM-020", "Los parámetros deben ser diferentes para el canal 6.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACM_021(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWACM-021", "Cuenta no encontrada.", "Cuenta inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_022(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWACM-022", "El tipo de cuenta no es válido.", "Cuenta inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_023(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWACM-023", "Cuenta no encontrada para la persona y la aplicación.", "Cuenta inválida", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_024(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACM-024", "Monto no válido para actualización", "Monto inválido", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_025(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWACM-025", "Datos de cuentas no encontradas.", "Datos no encontrados", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_026(HttpStatus.NOT_IMPLEMENTED, "INVALID_DATA", "MDWACM-026", "No se encontró la cuenta para la persona.", "Datos no encontrados", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_029(HttpStatus.NOT_IMPLEMENTED, "ERROR_CONVERT", "MDWACM-029", "Error al convertir el importe", "Error al convertir", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_014(HttpStatus.CONFLICT, "INVALID_DATA", "MDWACM-014", "No se encontraron cuentas asociadas.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
