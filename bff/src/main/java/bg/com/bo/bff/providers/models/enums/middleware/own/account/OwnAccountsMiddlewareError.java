package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareError implements IMiddlewareError {

    MDWACM_002(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-002", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_008(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-008", "No se encontraron registros de los extractos.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_012(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "MDWACM-012", "No es posible cambiar los límites transaccionales en cuentas conjuntas.", "Cambios no permitidos", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_DB_PROCEDURE.getCode(), "MDWACM-013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_028(HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_LOG.getCode(), "MDWACM-028", "El registro de Log no puede ser procesado", "Error log", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_009(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-009", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWACM_010(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-010", "No se encontró límites de la cuenta, acércate a una agencia cercana.", "Límites no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_019(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_PARAMS.getCode(), "MDWACM-019", "Los parámetros deben ser los mismos para el canal 2.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACM_020(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_PARAMS.getCode(), "MDWACM-020", "Los parámetros deben ser diferentes para el canal 6.", "Parámetros inválidos", CategoryError.NO_HEADER_PARAMS_MW_ERROR.getCategoryId()),
    MDWACM_021(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACM-021", "Cuenta no encontrada.", ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_022(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACM-022", "El tipo de cuenta no es válido.", ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_023(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACM-023", "Cuenta no encontrada para la persona y la aplicación.", ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_024(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "MDWACM-024", "Monto no válido para actualización", "Monto inválido", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_025(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACM-025", "Datos de cuentas no encontradas.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_026(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_DATA.getCode(), "MDWACM-026", "No se encontró la cuenta para la persona.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_029(HttpStatus.NOT_IMPLEMENTED, "ERROR_CONVERT", "MDWACM-029", "Error al convertir el importe", "Error al convertir", CategoryError.MW_UNEXPECTED_FORMAT.getCategoryId()),
    MDWACM_014(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "MDWACM-014", "No se encontraron cuentas asociadas.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
