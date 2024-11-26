package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CreditCardTransactionMiddlewareError implements IMiddlewareError {
    MDWCCTM_001(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_PROCEDURE", "MDWCCTM-001", "Ocurrio un error en el proceso"),
    MDWCCTM_002(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_ERROR", "MDWCCTM-002", "Ocurrio un error en el proceso de pago"),
    MDWCCTM_003(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWCCTM-003", "Error al obtener los datos de la cuenta"),
    MDWCCTM_004(HttpStatus.NOT_ACCEPTABLE, "ACCOUNT_ORIGIN_BLOCKED_FOR_DEBIT", "MDWCCTM-004", "La cuenta de origen está bloqueada para hacer débitos"),
    MDWCCTM_005(HttpStatus.BAD_REQUEST, "ERROR_CONVERTION_AMOUNT", "MDWCCTM-005", "Error al convertir el monto"),
    MDWCCTM_006(HttpStatus.NOT_ACCEPTABLE, "ERROR_ITF", "MDWCCTM-006", "Error al obtener el ITF"),
    MDWCCTM_007(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWCCTM-007", "Error, no tiene fondos suficientes"),
    MDWCCTM_008(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWCCTM-008", "No se pudo realizar la transferencia, límites de monto superados"),
    MDWCCTM_009(HttpStatus.NOT_ACCEPTABLE, "VALIDATE_COMBINATION", "MDWCCTM-009", "No hay combinaciones de poderes para el pago"),
    MDWCCTM_010(HttpStatus.BAD_REQUEST, "NOT_FOUND_DATA", "MDWCCTM-010", "Datos no encontrados"),

    MDWCCTM_011(HttpStatus.BAD_REQUEST, "ACCOUNT_NOT_PERMIT", "MDWCCTM-011", "El estado de la cuenta no permite el pago por este canal"),
    MDWCCTM_012(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_DUPLICATE", "MDWCCTM-012", "No se pudo verificar la transacción duplicada"),
    MDWCCTM_013(HttpStatus.NOT_ACCEPTABLE, "TRANSFER_DUPLICATE", "MDWCCTM-013", "El pago ya ha sido realizado anteriormente"),
    MDWCCTM_014(HttpStatus.NOT_ACCEPTABLE, "ERROR_TRANSFER_DUPLICATE", "MDWCCTM-014", "Error al registrar una transacción duplicada"),
    MDWCCTM_015(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_TICKET", "MDWCCTM-015", "Ocurrio un error con la conexión con Linkser"),
    MDWCCTM_016(HttpStatus.INTERNAL_SERVER_ERROR, "TICKET_EQUALS", "MDWCCTM-016", "Ocurrio un error al obtener en el proceso"),
    MDWCCTM_017(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-017", "El nombre del esquema debe ser CompanyId para los canales 1 - 6"),
    MDWCCTM_018(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-018", "Datos invalidos para companyId"),
    MDWCCTM_019(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-019", "El nombre del esquema debe ser PersonId para el canal 2"),
    MDWCCTM_020(HttpStatus.BAD_REQUEST, "GET_CURRENCY_BY_TRANSACTION_TYPE_INVALID", "MDWCCTM-020", "Error al obtener la divisa por tipo de transacción"),

    MDWCCTM_021(HttpStatus.BAD_REQUEST, "CONVERTING_DATE_FORMAT", "MDWCCTM-021", "Error al convertir el formato de fecha"),
    MDWCCTM_022(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWCCTM-022", "No hay fondos disponibles en la cuenta"),
    MDWCCTM_023(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_MAE", "MDWCCTM-023", "La transacción no se pudo procesar"),
    MDWCCTM_024(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_AUTHORIZATION", "MDWCCTM-024", "Transacción no puede ser procesada, para insertar en autorización"),
    MDWCCTM_025(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_UPDATE_MAE", "MDWCCTM-025", "La transacción no se pudo procesar"),
    MDWCCTM_026(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWCCTM-026", "No se pudo realizar la transferencia, límites de cantidad superados"),
    MDWCCTM_027(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_NUMBER_TRANSACTION", "MDWCCTM-027", "Ocurrio un error al obtener el número de transacción"),
    MDWCCTM_028(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_OBTAIN_MAE", "MDWCCTM-028", "La transacción no se pudo procesar"),
    MDWCCTM_029(HttpStatus.NOT_ACCEPTABLE, "ERROR_ACCOUNTS_PERSON", "MDWCCTM-029", "No se puede procesar la transacción para la cuenta de origen"),
    MDWCCTM_030(HttpStatus.NOT_ACCEPTABLE, "ERROR_POWER_VALIDATION", "MDWCCTM-030", "La transferencia no puede ser procesada, por la validación de poderes"),

    MDWCCTM_031(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_WSTCPP", "MDWCCTM-031", "Error obtener respuesta o conexión servicio WSTCPP"),
    MDWCCTM_032(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_UPDATE_REQUEST_PAYMENT_SP", "MDWCCTM-032", "Error al ejecutar la solicitud de actualización de pago"),
    MDWCCTM_033(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-033", "No se puede procesar el pago, verifique las divisas"),
    MDWCCTM_034(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-034", "Codigo de monedas es incorrecto"),
    MDWCCTM_035(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_VERIFY_TICKET", "MDWCCTM-035", "Error al intentar verificar el ticket"),
    MDWCCTM_038(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCTM-038", "No se puede procesar la transacción, para el usuario rol"),
    MDWCCTM_039(HttpStatus.NOT_ACCEPTABLE, "ERROR_VERIFY_POWER_TRX", "MDWCCTM-039", "No se han podido verificar los poderes en TRX, no se puede procesar la transacción"),

    PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "El pago de la tarjeta de crédito está pendiente de aprobación por el titular. Por favor, espera la confirmación");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
