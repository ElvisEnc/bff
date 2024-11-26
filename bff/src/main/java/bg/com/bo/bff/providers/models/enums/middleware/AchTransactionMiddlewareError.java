package bg.com.bo.bff.providers.models.enums.middleware;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AchTransactionMiddlewareError implements IMiddlewareError {
    MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Los parámetros enviados son inválidos.","Datos inválidos"),
    MDWACH_001(HttpStatus.CONFLICT, "TRANSACTION_NOT_ALLOWED", "MDWACH-001", "Las transacciones en moneda extranjera están temporalmente deshabilitadas.","Transacción no disponible"),
    MDWACH_002(HttpStatus.CONFLICT, "TRANSFER_DUPLICATE", "MDWACH-002", "Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?","Pago duplicado"),
    MDWACH_003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-003", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_004(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-004", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_005(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWACH-005", "Se requiere la autorización de otro firmante para completar la transacción.","Transacción pendiente"),
    MDWACH_006(HttpStatus.INTERNAL_SERVER_ERROR, "SOURCE_ACCOUNT_KO_001", "MDWACH-006", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_007(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY", "MDWACH-007", "Problemas con el tipo de la moneda.","Moneda inválida"),
    MDWACH_008(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_CONVERT", "MDWACH-008", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_009(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ITF", "MDWACH-009", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_010(HttpStatus.BAD_REQUEST, "AMOUNT_LIMIT_KO", "MDWACH-010", "El límite de monto ha sido superado para esta transacción.", "Límite de monto excedido"),
    MDWACH_011(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWACH-011", "Verifica el saldo disponible e intenta nuevamente.", "Saldo insuficiente"),
    MDWACH_012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-012", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_013(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-013", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_014(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-014", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_015(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_KO_001", "MDWACH-015", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_016(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_002", "MDWACH-016", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva"),
    MDWACH_017(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-017", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_018(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE", "MDWACH-018", "Tienes un pago similar realizado hace poco. ¿Quieres repetir el pago con el mismo monto?","Pago duplicado"),
    MDWACH_019(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWACH-019", "Es necesario indicar el origen y destino de los fondos.", "Origen y destino de fondos requeridos"),
    MDWACH_020(HttpStatus.CONFLICT, "DATA_INVALID", "MDWACH-020", "La cuenta de destino es inválida.","Ocurrió un problema"),
    MDWACH_021(HttpStatus.BAD_REQUEST, "ACCOUNT_KO", "MDWACH-021", "Tipo de cuenta inválida.","Cuenta inválida"),
    MDWACH_022(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-022", "Parámetros de tamaño incorrectos","Parámetros inválidos"),
    MDWACH_023(HttpStatus.BAD_REQUEST, "ERROR_ACH_ACCOUNT_NUMBER", "MDWACH-023", "Error en el nombre del esquema de la cuenta de crédito","Ocurrió un problema"),
    MDWACH_024(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_KO", "MDWACH-024", "Error al obtener los datos de la cuenta de destino.", "Error en cuenta de destino"),
    MDWACH_025(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_001", "MDWACH-025", "Identificación secundaria inválida","Identificación inválida"),
    MDWACH_026(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_002", "MDWACH-026", "Identificación del documento inválida","Identificación inválida"),
    MDWACH_027(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_003", "MDWACH-027", "Nombre inválido","Identificación inválida"),
    MDWACH_028(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-028", "Tamaño de los parámetros invalidos","Parámetros inválidos"),
    MDWACH_029(HttpStatus.BAD_REQUEST, "CRYPTO_FOUND", "MDWACH-029", "Esta operación presenta relación con la comercialización de criptomonedas y no puede realizarse.","Operación no permitida"),
    MDWACH_030(HttpStatus.BAD_REQUEST, "TRANSFER_KO_002", "MDWACH-030", "Transacción no procesada, rol de usuario no válido","Transacción no procesada"),
    MDWACH_031(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-031", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_032(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-032", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_033(HttpStatus.BAD_REQUEST, "TRANSFER_KO_003", "MDWACH-033", "No hay combinación válida de poderes para esta transacción.", "Transacción pendiente"),
    MDWACH_034(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-034", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_035(HttpStatus.BAD_REQUEST, "QUANTITY_LIMIT_KO", "MDWACH-035", "Límite de cantidad ha sido superado para esta transacción.", "Límite de cantidad excedido"),
    MDWACH_036(HttpStatus.INTERNAL_SERVER_ERROR, "UPDATE_QUANTITY_LIMIT_KO", "MDWACH-036", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_037(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-037", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_038(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-038", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_039(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-039", "Tuvimos un problema interno. Inténtalo nuevamente.","Ocurrió un problema"),
    MDWACH_040(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-040", "Parámetros inválidos","Parámetros inválidos"),
    MDWACH_041(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-041", "Canal inválido","Canal inválido"),
    MDWACH_043(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-043", "La cuenta no pertenece al usuario.","Ocurrió un problema"),;
    
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
