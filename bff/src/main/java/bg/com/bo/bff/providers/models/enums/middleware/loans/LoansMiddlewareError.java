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
    MDWPRE_001(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-001", "No se encontraron registros de pagos", "",0),
    MDWPRE_004(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-004", "No se encontraron registros de pagos de seguro", "",0),
    MDWPRE_006(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-006", "No se encontraron registros", "",0),
    MDWPRE_007(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DB_EXECUTION", "MDWPRE-007", "Ocurrio un error al obtener datos.", "",0),
    MDWPRE_008(HttpStatus.NOT_FOUND, "NOT_FOUND", "MDWPRE-008", "No se encontraron préstamos para el cliente", "",0),
    MDWPRE_010(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_TOPAZ_PROCEDURE.getCode(), "MDWPRE-010", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(),CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWPRE_900(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DB_EXECUTION", "MDWPRE-900", "Ocurrio un error al obtener datos.", "",0),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWPGL-400", "El número de préstamo no se encontró en los registros", "",0),

    MDWPRE_NOT_FOUND(HttpStatus.CONFLICT,  CodeError.DATA_NOT_FOUND.getCode(), null, "No se pudo obtener los datos del préstamo.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),

    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
