package bg.com.bo.bff.providers.models.enums.middleware.loans;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoansTransactionMiddlewareError implements IMiddlewareError {
    MDWLTM_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLTM-004", "Se presento problemas al verificar el pago de la cuota","",0),
    MDWLTM_005(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWLTM-005", "Esta transacción ya ha sido realizada anteriormente","",0),
    MDWLTM_006(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWLTM-006", "No se pudo verificar la transacción duplicada","",0),
    MDWLTM_007(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWLTM-007", "La cuenta de origen no se encuentra operativa","",0),
    MDWLTM_008(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWLTM-008", "La cuenta de origen no puede realizar débitos","",0),
    MDWLTM_009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWLTM-009", "No se pudo realizar el pago","",0),
    MDWLTM_010(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM-010", "No se pudo realizar la conversión del monto","",0),
    MDWLTM_011(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM-011", "No se pudo obtener los datos del préstamo","",0),
    MDWLTM_012(HttpStatus.BAD_REQUEST, "ERROR_ITF", "MDWLTM-012", "No se pudo realizar el cálculo del ITF","",0),
    MDWLTM_013(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWLTM-013", "La cuenta no tiene fondos suficientes para realizar el pago","",0),
    MDWLTM_014(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWLTM-014", "No se pudo realizar el pago, límites de monto superados","",0),
    MDWLTM_015(HttpStatus.BAD_REQUEST, "VALIDATE_ROLE_PERSON", "MDWLTM-015", "Validación del person rol fallida","",0),
    MDWLTM_016(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM-016", "No se pudo obtener los datos del préstamo","",0),
    MDWLTM_017(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM-017", "No se pudo registrar el pago","",0),
    MDWLTM_018(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM-018", "No se pudo registrar el pago","",0),
    MDWLTM_019(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM-019", "No se pudo registrar el pago","",0),
    MDWLTM_020(HttpStatus.BAD_REQUEST, "LOAN_TRANSFER_ERROR", "MDWLTM-020", "No se pudo realizar el pago del préstamo","",0),
    MDWLTM_032(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWLTM-032", "Estructura del cuerpo inválida. Parámetros inválidos","",0),

    MDWLTM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", null, "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente", CategoryError.SUCCESS.getCategoryId()),
    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
