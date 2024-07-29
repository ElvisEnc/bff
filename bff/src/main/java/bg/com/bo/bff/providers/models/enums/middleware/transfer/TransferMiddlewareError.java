package bg.com.bo.bff.providers.models.enums.middleware.transfer;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TransferMiddlewareError implements IMiddlewareError {
    MDWTRM_001(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-001", "No se pudo obtener datos de la cuenta de origen"),
    MDWTRM_002(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-002", "La cuenta de origen no se encuentra operativa"),
    MDWTRM_003(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_NOT_FOUND", "MDWTRM-003", "Error al obtener datos de la cuenta de destino"),
    MDWTRM_004(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_KO", "MDWTRM-004", "La cuenta de destino no se encuentra operativa"),
    MDWTRM_005(HttpStatus.BAD_REQUEST, "SAME_ACCOUNT", "MDWTRM-005", "La cuenta de origen y de destino no pueden ser la misma"),
    MDWTRM_006(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWTRM-006", "No se pudo realizar la conversión del monto"),
    MDWTRM_007(HttpStatus.BAD_REQUEST, "ERROR_ITF", "MDWTRM-007", "No se pudo realizar el cálculo del ITF"),
    MDWTRM_008(HttpStatus.BAD_REQUEST, "NO_FUNDS_AVAILABLE", "MDWTRM-008", "La cuenta no tiene fondos suficientes para realizar la transferencia"),
    MDWTRM_009(HttpStatus.BAD_REQUEST, "VALIDATE_LIMITS", "MDWTRM-009", "No se pudo validar los limites"),
    MDWTRM_010(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-010", "No se pudo realizar la transferencia, límites superados"),
    MDWTRM_011(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWTRM-011", "Se requieren permisos de firmantes para realizar la transferencia"),
    MDWTRM_012(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-012", "La moneda es distinta a la de la cuenta a debitar y a la de acreditar"),
    MDWTRM_013(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWTRM-013", "No se pudo obtener los datos del préstamo"),
    MDWTRM_014(HttpStatus.BAD_REQUEST, "PAYMENT_LOAND_ERROR", "MDWTRM-014", "Se produjo un error al procesar el pago del préstamo"),
    MDWTRM_015(HttpStatus.BAD_REQUEST, "NOT_FOUND_RECEIPT", "MDWTRM-015", "Hubo un problema al recuperar los datos del bono"),
    MDWTRM_016(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWTRM-016", "La cuenta de origen no puede realizar débitos"),
    MDWTRM_017(HttpStatus.BAD_REQUEST, "TRANSFER_KO", "MDWTRM-017", "Se produjo un error al validar la transferencia"),
    MDWTRM_018(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWTRM-018", "Problemas con el tipo de la moneda"),
    MDWTRM_019(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWTRM-019", "No se pudo realizar la transferencia"),
    MDWTRM_020(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-020", "Esta transacción ya ha sido realizada anteriormente"),
    MDWTRM_021(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-021", "No se pudo verificar la transacción duplicada"),
    MDWTRM_022(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWTRM-022", "No se pudo verificar la transacción duplicada"),
    MDWTRM_023(HttpStatus.BAD_REQUEST, "TRANSACTION_NOT_ALLOWED", "MDWTRM-023", "No se pudo realizar la transacción, no esta permitida"),
    MDWTRM_024(HttpStatus.BAD_REQUEST, "LIST_KO", "MDWTRM-024", "No se pudo obtener la lista de cuentas"),
    MDWTRM_026(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWTRM-026", "El origen y destino de fondos son requeridos"),
    MDWTRM_032(HttpStatus.BAD_REQUEST, "OWNER_ACCOUNT_KO", "MDWTRM-032", "La cuenta de origen no se encuentra operativa"),
    MDWTRM_033(HttpStatus.BAD_REQUEST, "CRIPTO_FOUND", "MDWTRM-033", "La transferencia no puede ser procesada, por validación de criptomoneda"),
    MDWTRM_034(HttpStatus.BAD_REQUEST, "TRACKING_ERROR", "MDWTRM-034", "La transferencia no puede ser procesada, mo se pudo registar el registro"),
    MDWTRM_035(HttpStatus.BAD_REQUEST, "TRACKING_UPDATE_ERROR", "MDWTRM-035", "La transferencia no puede ser procesada, no se pudo actualizar el seguimiento"),
    MDWTRM_036(HttpStatus.BAD_REQUEST, "AUTHORIZATION_REGISTER_ERROR", "MDWTRM-036", "La transferencia no puede ser procesada, porque no se pudo procesar el registro de autorización"),
    MDWTRM_037(HttpStatus.BAD_REQUEST, "BLACKLIST_FOUND", "MDWTRM-037", "La cuenta de origen se encuentra en una lista negra interna"),
    MDWTRM_038(HttpStatus.BAD_REQUEST, "ERROR_VERIFY_POWER_TRX", "MDWTRM-038", "La transferencia no puede ser procesada, por la validación de poderes trx"),
    MDWTRM_039(HttpStatus.BAD_REQUEST, "VALIDATE_ROLE_PERSON", "MDWTRM-039", "Validación del person rol fallida"),
    MDWTRM_040(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-040", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_041(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-041", "No se pudo realizar la transferencia, actualiza el límite diario"),
    MDWTRM_042(HttpStatus.BAD_REQUEST, "LIMIT_KO", "MDWTRM-042", "No se pudo validar los limites por día de la cuenta"),
    MDWTRM_043(HttpStatus.BAD_REQUEST, "ERROR_GET_AUTHORIZATION", "MDWTRM-043", "La transferencia no puede ser procesada, error al obtener la auth maestro"),
    MDWTRM_044(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID_NOT_NULL", "MDWTRM-044", "El codigo de persona no puede ser nulo"),
    MDWTRM_045(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID_NOT_NULL", "MDWTRM-045", "El companyId no puede ser nulo"),
    MDWTRM_046(HttpStatus.BAD_REQUEST, "ERROR_COMPANY_ID", "MDWTRM-046", "SchemeName sólo puede ser companyId"),
    MDWTRM_047(HttpStatus.BAD_REQUEST, "ERROR_PERSON_ID", "MDWTRM-047", "SchemeName sólo puede ser personId"),
    MDWTRM_048(HttpStatus.BAD_REQUEST, "ERROR_GET_ACCOUNT", "MDWTRM-048", "Ocurrio un error al obtener la cuenta"),
    MDWTRM_049(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNT_VALIDATE", "MDWTRM-049", "La cuenta no pertenece al usuario"),
    MDWTRM_050(HttpStatus.BAD_REQUEST, "ERROR_WORKFLOW_INVALID", "MDWTRM-050", "Ocurrio un error"),
    MDWTRM_051(HttpStatus.BAD_REQUEST, "ACCOUNT_DESTINY_INVALID", "MDWTRM-051", "La cuenta de destino es inválida"),
    MDWTRM_052(HttpStatus.BAD_REQUEST, "ACCOUNT_ORIGIN_INVALID", "MDWTRM-052", "La cuenta de origen es inválida"),
    MDWTRM_053(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_TRANSACTION", "MDWTRM-053", "Error al obtener el número de transacción"),
    MDWTRM_054(HttpStatus.BAD_REQUEST, "ERROR_POWER_VALIDATION", "MDWTRM-054", "Ocurrio un error, al validar los poderes"),
    MDWTRM_055(HttpStatus.BAD_REQUEST, "POWER_VALIDATION", "MDWTRM-055", "No tiene autorización para realizar la transacción"),
    MDWTRM_056(HttpStatus.BAD_REQUEST, "TRUSTED_ACCOUNT", "MDWTRM-056", "La cuenta no está en la lista de confianza"),
    MDWTRM_400(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWPGL-400", "Problemas con el tipo de la moneda"),
    MDWTRM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "La transferencia está pendiente de aprobación por el titular. Por favor, espera la confirmación");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
