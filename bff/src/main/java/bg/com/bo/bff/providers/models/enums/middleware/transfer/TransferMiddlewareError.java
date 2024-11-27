package bg.com.bo.bff.providers.models.enums.middleware.transfer;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransferMiddlewareError implements IMiddlewareError {
    MDWTRM_001(HttpStatus.INTERNAL_SERVER_ERROR, "SOURCE_ACCOUNT_KO", "MDWTRM-001", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_002(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-002", "No es posible utilizar esta cuenta para realizar esta transacción.","Transacción no disponible"),
    MDWTRM_003(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_NOT_FOUND", "MDWTRM-003", "Tuvimos un problema al obtener la cuenta de destino. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_004(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_KO", "MDWTRM-004", "No es posible utilizar esta cuenta para realizar esta transacción.","Cuenta de destino inactiva"),
    MDWTRM_005(HttpStatus.BAD_REQUEST, "SAME_ACCOUNT", "MDWTRM-005", "La cuenta de origen y de destino no pueden ser la misma.","Cuenta de origen y destino iguales"),
    MDWTRM_006(HttpStatus.INTERNAL_SERVER_ERROR, "NOT_FOUND_AVAILABLE", "MDWTRM-006", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWTRM_007(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ITF", "MDWTRM-007", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_008(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWTRM-008", "Verifica el saldo disponible e intenta nuevamente.","Saldo insuficiente"),
    MDWTRM_009(HttpStatus.NOT_ACCEPTABLE, "VALIDATE_LIMITS", "MDWTRM-009", "No se pudo validar el límite de la cuenta.","Límite de cuenta"),
    MDWTRM_010(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWTRM-010", "El límite de monto ha sido superado para esta transacción.","Límite de monto excedido"),
    MDWTRM_011(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWTRM-011", "Se requiere la autorización de otro firmante para completar la transacción.","Transacción pendiente"),
    MDWTRM_012(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-012", "La moneda de la cuenta origen es diferente.","Moneda de cuenta origen diferente"),
    MDWTRM_013(HttpStatus.INTERNAL_SERVER_ERROR, "NOT_FOUND_AVAILABLE", "MDWTRM-013", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_014(HttpStatus.INTERNAL_SERVER_ERROR, "PAYMENT_LOAND_ERROR", "MDWTRM-014", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_015(HttpStatus.INTERNAL_SERVER_ERROR, "NOT_FOUND_RECEIPT", "MDWTRM-015", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_016(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-016", "No es posible utilizar esta cuenta para realizar esta transacción.","Cuenta de origen inactiva"),
    MDWTRM_017(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_KO", "MDWTRM-017", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_018(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWTRM-018", "Problemas con el tipo de la moneda.","Moneda inválida"),
    MDWTRM_019(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTRM-019", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_020(HttpStatus.CONFLICT, "TRANSFER_DUPLICATE", "MDWTRM-020", "Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?","Pago duplicado"),
    MDWTRM_021(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_DUPLICATE", "MDWTRM-021", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_022(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_DUPLICATE", "MDWTRM-022", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_023(HttpStatus.CONFLICT, "TRANSACTION_NOT_ALLOWED", "MDWTRM-023", "Las transacciones en moneda extranjera están temporalmente deshabilitadas.","Transacción no disponible"),
    MDWTRM_024(HttpStatus.INTERNAL_SERVER_ERROR, "LIST_KO", "MDWTRM-024", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_026(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWTRM-026", "Es necesario indicar el origen y destino de los fondos.","Origen y destino de fondos requeridos"),
    MDWTRM_032(HttpStatus.BAD_REQUEST, "OWNER_ACCOUNT_KO", "MDWTRM-032", "No es posible utilizar esta cuenta para realizar esta transacción.","Cuenta de origen inactiva"),
    MDWTRM_033(HttpStatus.BAD_REQUEST, "CRYPTO_FOUND", "MDWTRM-033", "Esta operación presenta relación con la comercialización de criptomonedas y no puede realizarse.","Operación no permitida"),
    MDWTRM_034(HttpStatus.INTERNAL_SERVER_ERROR, "TRACKING_ERROR", "MDWTRM-034", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_035(HttpStatus.INTERNAL_SERVER_ERROR, "TRACKING_UPDATE_ERROR", "MDWTRM-035", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_036(HttpStatus.INTERNAL_SERVER_ERROR, "AUTHORIZATION_REGISTER_ERROR", "MDWTRM-036", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_037(HttpStatus.BAD_REQUEST, "BLACKLIST_FOUND", "MDWTRM-037", "La cuenta de origen se encuentra en una lista negra interna.","Ocurrió un problema"),
    MDWTRM_038(HttpStatus.BAD_REQUEST, "ERROR_VERIFY_POWER_TRX", "MDWTRM-038", "La transferencia no puede ser procesada, por la validación de poderes.","Ocurrió un problema"),
    MDWTRM_039(HttpStatus.BAD_REQUEST, "VALIDATE_ROLE_PERSON", "MDWTRM-039", "Validación del person rol fallida.","Ocurrió un problema"),
    MDWTRM_040(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWTRM-040", "Límite de cantidad ha sido superado para esta transacción.","Límite de cantidad excedido"),
    MDWTRM_041(HttpStatus.INTERNAL_SERVER_ERROR, "UPDATE_QUANTITY_LIMIT_KO", "MDWTRM-041", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_042(HttpStatus.INTERNAL_SERVER_ERROR, "QUANTITY_LIMIT_VALIDATION_KO", "MDWTRM-042", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_043(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_GET_AUTHORIZATION", "MDWTRM-043", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_044(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID_NOT_NULL", "MDWTRM-044", "El codigo de persona no puede ser nulo","Ocurrió un problema"),
    MDWTRM_045(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID_NOT_NULL", "MDWTRM-045", "El companyId no puede ser nulo","Ocurrió un problema"),
    MDWTRM_046(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID", "MDWTRM-046", "SchemeName sólo puede ser companyId","Ocurrió un problema"),
    MDWTRM_047(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID", "MDWTRM-047", "SchemeName sólo puede ser personId","Ocurrió un problema"),
    MDWTRM_048(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_GET_ACCOUNT", "MDWTRM-048", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_049(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNT_VALIDATE", "MDWTRM-049", "La cuenta no pertenece al usuario","Ocurrió un problema"),
    MDWTRM_050(HttpStatus.BAD_REQUEST, "ERROR_WORKFLOW_INVALID", "MDWTRM-050", "Ocurrio un error","Ocurrió un problema"),
    MDWTRM_051(HttpStatus.BAD_REQUEST, "ACCOUNT_DESTINY_INVALID", "MDWTRM-051", "La cuenta de destino es inválida.","Ocurrió un problema"),
    MDWTRM_052(HttpStatus.BAD_REQUEST, "ACCOUNT_ORIGIN_INVALID", "MDWTRM-052", "La cuenta de origen es inválida.","Ocurrió un problema"),
    MDWTRM_053(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_NUMBER_TRANSACTION", "MDWTRM-053", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_054(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_POWER_VALIDATION", "MDWTRM-054", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWTRM_055(HttpStatus.BAD_REQUEST, "POWER_VALIDATION", "MDWTRM-055", "Se requiere la autorización de otro firmante para completar la transacción.","Transacción pendiente"),
    MDWTRM_056(HttpStatus.BAD_REQUEST, "TRUSTED_ACCOUNT", "MDWTRM-056", "La cuenta no está en la lista de confianza.","Ocurrió un problema"),
    MDWTRM_059(HttpStatus.NOT_ACCEPTABLE, "INVALID_DATA", "MDWTRM-059", "No se pudo validar la transacción PCC01.","Ocurrió un problema"),
    MDWTRM_063(HttpStatus.NOT_ACCEPTABLE, "INVALID_DATA", "MDWTRM-063", "La cuenta de destino es inválida.","Ocurrió un problema"),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Los parámetros enviados son inválidos.","Datos inválidos"),
    MDWTRM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "Se requiere la autorización de otro firmante para completar la transacción.","Transacción pendiente"),
    MDWTRM_CRYPTO(HttpStatus.CONFLICT, "CRYPTO_FOUND", null, "Estimado Cliente: las plataformas transaccionales y la compraventa de Activos Virtuales no está bajo control, supervisión ni regulación de ASFI. Estas transacciones son a riesgo del cliente. Gracias.","Transacción no permitida");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
