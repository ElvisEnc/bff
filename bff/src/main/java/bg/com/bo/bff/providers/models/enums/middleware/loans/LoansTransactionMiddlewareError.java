package bg.com.bo.bff.providers.models.enums.middleware.loans;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LoansTransactionMiddlewareError implements IMiddlewareError {
    MDWLTM_004(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWLTM-004", "Se presento problemas al verificar el pago de la cuota"),
    MDWLTM_005(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWLTM-005", "Esta transacción ya ha sido realizada anteriormente"),
    MDWLTM_006(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWLTM-006", "No se pudo verificar la transacción duplicada"),
    MDWLTM_007(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWLTM-007", "La cuenta de origen no se encuentra operativa"),
    MDWLTM_008(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWLTM_008", "La cuenta de origen no puede realizar débitos"),
    MDWLTM_009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWLTM_009", "No se pudo realizar el pago"),
    MDWLTM_010(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM_010", "No se pudo realizar la conversión del monto"),
    MDWLTM_011(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM_011", "No se pudo obtener los datos del préstamo"),
    MDWLTM_012(HttpStatus.BAD_REQUEST, "ERROR_ITF", "MDWLTM_012", "No se pudo realizar el cálculo del ITF"),
    MDWLTM_013(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWLTM_013", "La cuenta no tiene fondos suficientes para realizar el pago"),
    MDWLTM_014(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWLTM_014", "No se pudo realizar el pago, límites superados"),
    MDWLTM_015(HttpStatus.BAD_REQUEST, "VALIDATE_ROLE_PERSON", "MDWLTM_015", "Validación del person rol fallida"),
    MDWLTM_016(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWLTM_016", "No se pudo obtener los datos del préstamo"),
    MDWLTM_017(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM_017", "No se pudo registrar el pago"),
    MDWLTM_018(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM_018", "No se pudo registrar el pago"),
    MDWLTM_019(HttpStatus.BAD_REQUEST, "ERROR_MAE", "MDWLTM_019", "No se pudo registrar el pago"),
    MDWLTM_020(HttpStatus.BAD_REQUEST, "LOAN_TRANSFER_ERROR", "MDWLTM_020", "No se pudo realizar el pago del préstamo");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
