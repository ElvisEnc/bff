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
public enum LoansTransactionMiddlewareError implements IMiddlewareError {

    MDWLTM_004(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-004", "Se presento problemas al verificar el pago de la cuota.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_005(HttpStatus.CONFLICT, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWLTM-005", ConstantMessages.TRANSFER_DUPLICATE.getMessage(), ConstantMessages.TRANSFER_DUPLICATE.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWLTM_006(HttpStatus.NOT_ACCEPTABLE, CodeError.TRANSFER_DUPLICATE.getCode(), "MDWLTM-006", "No se pudo verificar la transacción duplicada.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_007(HttpStatus.CONFLICT, CodeError.ACCOUNT_BLOCKED.getCode(), "MDWLTM-007", "La cuenta de origen no se encuentra operativa.", ConstantMessages.ACCOUNT_BLOCKED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWLTM_008(HttpStatus.CONFLICT, CodeError.ACCOUNT_BLOCKED.getCode(), "MDWLTM-008", "La cuenta de origen no puede realizar débitos.", ConstantMessages.ACCOUNT_BLOCKED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWLTM_009(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-009", "No se pudo realizar el pago.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_010(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-010", "No se pudo realizar la conversión del monto.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_011(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-011", "No se pudo obtener los datos del préstamo.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_012(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_ITF.getCode(), "MDWLTM-012", "No se pudo realizar el cálculo del ITF.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_SPECIFIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_013(HttpStatus.CONFLICT, CodeError.NO_FUNDS_AVAILABLE.getCode(), "MDWLTM-013", "La cuenta no tiene fondos suficientes para realizar el pago", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWLTM_014(HttpStatus.CONFLICT, CodeError.AMOUNT_LIMIT_KO.getCode(), "MDWLTM-014", ConstantMessages.AMOUNT_LIMIT_KO.getMessage(), ConstantMessages.AMOUNT_LIMIT_KO.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWLTM_015(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-015", "Validación del person rol fallida.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_016(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "MDWLTM-016", "No se pudo obtener los datos del préstamo.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_017(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_MAE.getCode(), "MDWLTM-017", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_018(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_MAE.getCode(), "MDWLTM-018", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_019(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_MAE.getCode(), "MDWLTM-019", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_020(HttpStatus.NOT_ACCEPTABLE, "LOAN_TRANSFER_ERROR", "MDWLTM-020", "No se pudo realizar el pago del préstamo, intente más tarde.", ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    MDWLTM_032(HttpStatus.NOT_IMPLEMENTED, CodeError.INVALID_PARAMS.getCode(), "MDWLTM-032", "Estructura del cuerpo inválida. Parámetros inválidos", "Parámetros inválidos", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()),

    MDWLTM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", null, "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente", CategoryError.SUCCESS.getCategoryId()),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
