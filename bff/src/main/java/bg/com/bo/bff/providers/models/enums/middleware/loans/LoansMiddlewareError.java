package bg.com.bo.bff.providers.models.enums.middleware.loans;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoansMiddlewareError implements IMiddlewareError {

    MDWPRE_001(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPRE-001", "No se encontraron registros de pagos.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWPRE_004(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPRE-004", "No se encontraron registros de pagos de seguro.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWPRE_006(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPRE-006", "No se encontraron registros", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWPRE_007(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_DB_PROCEDURE.getCode(), "MDWPRE-007", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPRE_008(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPRE-008", "No se encontraron préstamos para el cliente.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()),
    MDWPRE_010(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TOPAZ_PROCEDURE.getCode(), "MDWPRE-010", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPRE_900(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_DB_PROCEDURE.getCode(), "MDWPRE-900", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),

    MDWPRE_NOT_FOUND(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), null, "No se pudo obtener los datos del préstamo.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
