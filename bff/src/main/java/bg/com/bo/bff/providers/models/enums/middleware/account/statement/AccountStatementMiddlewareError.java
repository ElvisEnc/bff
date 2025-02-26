package bg.com.bo.bff.providers.models.enums.middleware.account.statement;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountStatementMiddlewareError implements IMiddlewareError {

    MDWACM_002(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-002", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACM-003", "No se pudo obtener la información de los bancos, intente más tarde.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWACM-004", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_009(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-009", ConstantMessages.NO_FOUND_DATA.getMessage(), ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_010(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-010", "No se encontraron límites para la cuenta, acercate a una oficina cercana.", ConstantMessages.NO_FOUND_DATA.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWACM_012(HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWACM-012", "No se encontraron registros en el rango de fecha dado.", "Datos no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_013(HttpStatus.CONFLICT, CodeError.INVALID_DATA.getCode(), "MDWACM-013", ConstantMessages.INVALID_ACCOUNT.getMessage(), ConstantMessages.INVALID_ACCOUNT.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWACM_014(HttpStatus.BAD_REQUEST, CodeError.INVALID_DATA.getCode(), "MDWACM-014", "El rango máximo permitido es de un mes.", "Fechas inválidas", CategoryError.INVALID_FORMAT.getCategoryId()),
    MDWACM_027(HttpStatus.CONFLICT, CodeError.QUANTITY_LIMIT_KO.getCode(), "MDWACM-027", "Excedió la cantidad máxima permitida de límites.", ConstantMessages.QUANTITY_LIMIT_KO.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    INVALID_SEARCH_PARAMS(HttpStatus.BAD_REQUEST, CodeError.INVALID_PARAMS.getCode(), null, "searchCriteria parameters inválidos: TOACCOUNTNUMBER, AMOUNT y DESCRIPTION son aceptados.", "Parámetros inválidos", CategoryError.INVALID_FORMAT.getCategoryId()),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
