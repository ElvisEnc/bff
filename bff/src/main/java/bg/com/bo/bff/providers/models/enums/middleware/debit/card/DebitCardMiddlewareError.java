package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareError implements IMiddlewareError {

    MDWTJD_001(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWTJD-001", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTJD_002(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "MDWTJD-002", "Número de identificación inválido.", ConstantMessages.INVALID_USER_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTJD_003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWTJD-003", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_004(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWTJD-004", "No se encontraron tarjetas de debito.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPGL_004(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPGL-004", "No se encontraron tarjetas de debito.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPGL_005(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPGL-005", "No se encontraron autorizaciones de límites para compras por Internet.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTJD_005(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWTJD-005", "No se encontraron autorizaciones de límites para compras por Internet.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTJD_006(HttpStatus.NOT_ACCEPTABLE, "ERROR_BLOCK_CARD", "MDWTJD-006", "No se pudo realizar el cambio de estado de la tarjeta.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_007(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TOPAZ_PROCEDURE.getCode(), "MDWTJD-007", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_008(HttpStatus.NOT_ACCEPTABLE, CodeError.INVALID_ACTION.getCode(), "MDWTJD-008", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_009(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWTJD-009", "No se pudo activar la Tarjeta de Debito, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_010(HttpStatus.NOT_ACCEPTABLE, "ERROR_ACTIVATE_INSURANCE", "MDWTJD-010", "No se pudo activar el seguro de la Tarjeta de Debito, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_011(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_LOG.getCode(), "MDWTJD-011", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_012(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWTJD-012", "No se pudo modificar el orden de las cuentas.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_013(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWTJD-013", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_014(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWTJD-014", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_015(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWTJD-015", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWTJD_900(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_DB_PROCEDURE.getCode(), "MDWTJD-900", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_3002(HttpStatus.NOT_ACCEPTABLE, "ERROR_ACTIVATE_INSURANCE", "MDWTJD-3002", "No se pudo activar el seguro, no tiene cuenta asociada.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_016(HttpStatus.NOT_ACCEPTABLE, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWTJD-016", "Ya tienes una habilitación activa en las fechas seleccionadas. Ajusta el rango o espera a que termine la habilitación actual.", "Rango de fechas duplicado", CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWTJD_017(HttpStatus.CONFLICT, "BAD_REQUEST", "MDWTJD-017", "La fecha final debe ser mayor que la fecha actual del sistema.", "Fecha no válida", CategoryError.INVALID_FORMAT.getCategoryId()),
    END_DATE_MUST_BE_GREATER_THAN_START_DATE(HttpStatus.BAD_REQUEST, "BAD_REQUEST", null, "la fecha fin debe ser mayor o igual a la fecha inicio", "Fecha invalida", CategoryError.INVALID_FORMAT.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
