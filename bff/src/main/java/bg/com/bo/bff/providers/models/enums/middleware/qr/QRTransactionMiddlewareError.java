package bg.com.bo.bff.providers.models.enums.middleware.qr;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QRTransactionMiddlewareError implements IMiddlewareError {

    //QR-TRANSACTION MANAGER
    MDWQTM_001(HttpStatus.BAD_REQUEST, "TRANSFER_USD_NOT_ALLOWED", "MDWQTM-001", "Las transacciones en moneda extranjera están temporalmente deshabilitadas.", "Transacción no disponible"),
    MDWQTM_002(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWQTM-002", "Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?", "Pago duplicado"),
    MDWQTM_003(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_REDIS_TRANSFER_DUPLICATE", "MDWQTM-003", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_004(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_REDIS_REGISTER_TRANSFER_DUPLICATE", "MDWQTM-004", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_005(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWQTM-005", "No hay combinación válida de poderes para esta transacción.", "Transacción pendiente"),
    MDWQTM_006(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWQTM-006", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_007(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWQTM-007", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_008(HttpStatus.NOT_ACCEPTABLE, "ERROR_CONVERTION_AMOUNT", "MDWQTM-008", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_009(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ITF", "MDWQTM-009", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_010(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWQTM-010", "El límite de monto ha sido superado para esta transacción.", "Límite de monto excedido"),
    MDWQTM_011(HttpStatus.BAD_REQUEST, "NO_FOUNDS_AVAILABLE", "MDWQTM-011", "Verifica el saldo disponible e intenta nuevamente.", "Saldo insuficiente"),
    MDWQTM_012(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_NUMBER_PLEDGE", "MDWQTM-012", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_013(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_REGISTER_PLEDGE", "MDWQTM-013", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_014(HttpStatus.BAD_REQUEST, "ERROR_NUMBER_ACH", "MDWQTM-014", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_015(HttpStatus.CONFLICT, "TRANSFER_ERROR", "MDWQTM-015", "La transacción no puede ser procesada para la cuenta a debitar.", "Transacción no disponible"),
    MDWQTM_016(HttpStatus.CONFLICT, "ACCOUNT_BLOCKED", "MDWQTM-016", "No es posible utilizar esta cuenta para realizar la transacción.", "Cuenta de origen inactiva"),
    MDWQTM_017(HttpStatus.CONFLICT, "CRYPTO_ACTIVE", "MDWQTM-017", "Esta operación presenta relación con la comercialización de criptomonedas y no puede realizarse.", "Operación no permitida"),
    MDWQTM_018(HttpStatus.BAD_REQUEST, "ERROR_ROL_USER_EMPRESA", "MDWQTM-018", "La transacción no puede ser procesada para el rol del usuario.", "Rol de usuario no autorizado"),
    MDWQTM_019(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_MAE", "MDWQTM-019", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_020(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_INSERT_AUTHORIZATION", "MDWQTM-020", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_021(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_VERIFY_POWER_TRX", "MDWQTM-021", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_022(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_UPDATE_MAE", "MDWQTM-022", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_023(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWQTM-023", "Límite de cantidad ha sido superado para esta transacción.", "Límite de cantidad excedido"),
    MDWQTM_024(HttpStatus.INTERNAL_SERVER_ERROR, "UPDATE_QUANTITY_LIMIT_KO", "MDWQTM-024", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_025(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_NUMBER_TRANSACTION", "MDWQTM-025", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_026(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_OBTAIN_MAE", "MDWQTM-026", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_027(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ACH_DATA", "MDWQTM-027", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_028(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_RELEASE_PLEDGE", "MDWQTM-028", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_029(HttpStatus.CONFLICT, "ERROR_REGISTER_MASTER_QR", "MDWQTM-029", "No se pudo realizar la transacción, error al registrar Maestro QR.", "Ocurrió un problema"),
    MDWQTM_030(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ACH_EXPRESS", "MDWQTM-030", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_031(HttpStatus.BAD_REQUEST, "ACCOUNT_DESTINY_BLOCKED_FOR_CREDIT", "MDWQTM-031", "La cuenta de destino está bloqueada para recibir créditos.", "Cuenta de destino bloqueada para créditos"),
    MDWQTM_032(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_DESTINY", "MDWQTM-032", "Error al obtener los datos de la cuenta de destino.", "Error en cuenta de destino"),
    MDWQTM_033(HttpStatus.BAD_REQUEST, "ERROR_ACCOUNTS_EQUALS", "MDWQTM-033", "No se puede procesar la transacción entre la misma cuenta de origen y destino.", "Cuentas de origen y destino iguales"),
    MDWQTM_034(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ACCOUNTS_PERSON", "MDWQTM-034", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWQTM_035(HttpStatus.BAD_REQUEST, "ERROR_POWER_OLD", "MDWQTM-035", "La transacción no puede ser procesada debido a poderes desactualizados.", "Poderes desactualizados"),
    MDWQTM_036(HttpStatus.BAD_REQUEST, "NOT_URL_INVALID", "MDWQTM-036", "La URL proporcionada es inválida.", "URL inválida"),
    MDWQTM_037(HttpStatus.BAD_REQUEST, "ERROR_DUE_DATE", "MDWQTM-037", "La fecha de vencimiento es incorrecta.", "Error en fecha de vencimiento"),
    MDWQTM_038(HttpStatus.BAD_REQUEST, "INVALID_CHANNEL", "MDWQTM-038", "Canal inválido para esta transacción.", "Canal inválido"),
    MDWQTM_039(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWQTM-039", "Es necesario indicar el origen y destino de los fondos.", "Origen y destino de fondos requeridos"),
    MDWQTM_040(HttpStatus.BAD_REQUEST, "QR_HAS_BEEN_USED", "MDWQTM-040", "El código QR ya ha sido utilizado.", "Código QR utilizado"),
    MDWQTM_041(HttpStatus.BAD_REQUEST, "ERROR_DATA_ACCOUNT_DESTINY", "MDWQTM-041", "Número de cuenta de destino inválida.", "Cuenta de destino inválida"),
    MDWQTM_042(HttpStatus.BAD_REQUEST, "DIFFERENT_AMOUNT", "MDWQTM-042", "El monto de la transacción es diferente al monto requerido.", "Monto de transacción diferente"),
    MDWQTM_043(HttpStatus.CONFLICT, "CHECK_TRANSFER", "MDWQTM-043", "Verifica si la transacción fue satisfactoria, revisa tu extracto.", "Tiempo excedido"),
    MDWQTM_044(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_DESTINATION_BANK", "MDWQTM-044", "La entidad financiera de destino esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible"),
    MDWQTM_045(HttpStatus.NOT_ACCEPTABLE, "CONTINGENCY_ORIGIN_BANK", "MDWQTM-045", "Nuestra entidad esta fuera de servicio para realizar esta transacción. Intentálo más tarde.", "Servicio no disponible"),
    MDWGQM_PENDING(HttpStatus.OK, "TRANSFER_PENDING", "MDWPGL-PENDING", "Se requiere la autorización de otro firmante para completar la transacción.", "Transacción pendiente");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
