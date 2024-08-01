package bg.com.bo.bff.providers.models.enums.middleware;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import org.springframework.http.HttpStatus;

@lombok.Getter
@lombok.AllArgsConstructor
public enum ACHMiddlewareError implements IMiddlewareError {
    MDWACH_001(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY_001", "MDWACH-001", "Transacción no permitida para esta moneda"),
    MDWACH_002(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE_001", "MDWACH-002", "Esta transacción ya ha sido realizada anteriormente"),
    MDWACH_003(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-003", "No se pudo verificar la transacción duplicada"),
    MDWACH_004(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-004", "No se pudo registrar la transacción duplicada"),
    MDWACH_005(HttpStatus.BAD_REQUEST, "VALIDATE_COMBINATION", "MDWACH-005", "Se requieren permisos de firmantes para realizar la transferencia"),
    MDWACH_006(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_001", "MDWACH-006", "Error al obtener datos de la cuenta de origen"),
    MDWACH_007(HttpStatus.BAD_REQUEST, "INVALID_CURRENCY_002", "MDWACH-007", "Moneda incorrecta"),
    MDWACH_008(HttpStatus.BAD_REQUEST, "ERROR_CONVERT", "MDWACH-008", "Error al convertir el monto"),
    MDWACH_009(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ITF", "MDWACH-009", "Error al calcular el ITF"),
    MDWACH_010(HttpStatus.BAD_REQUEST, "LIMIT_KO_001", "MDWACH-010", "Límite de monto excedido"),
    MDWACH_011(HttpStatus.BAD_REQUEST, "NOT_FOUND_AVAILABLE", "MDWACH-011", "Fondos insuficientes en la cuenta"),
    MDWACH_012(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-012", "Error al obtener el número de reserva"),
    MDWACH_013(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-013", "Error en la promesa y registro de ACH"),
    MDWACH_014(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-014", "Error al obtener el número de orden ACH"),
    MDWACH_015(HttpStatus.INTERNAL_SERVER_ERROR, "TRANSFER_KO_001", "MDWACH-015", "Error al procesar la transferencia. Revise los datos e intentelo nuevamente."),
    MDWACH_016(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO_002", "MDWACH-016", "La cuenta de origen no está autorizada para debitar"),
    MDWACH_017(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-017", "Error al verificar la orden ACH duplicada"),
    MDWACH_018(HttpStatus.BAD_REQUEST, "TRANSFER_DUPLICATE_002", "MDWACH-018", "Orden ACH duplicada"),
    MDWACH_019(HttpStatus.BAD_REQUEST, "FUNDS_REQUIRED", "MDWACH-019", "Se requiere especificar el origen y destino de los fondos"),
    MDWACH_020(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-020", "Error al obtener listado de cuentas de bancos externos"),
    MDWACH_021(HttpStatus.BAD_REQUEST, "ACCOUNT_KO", "MDWACH-021", "Tipo de cuenta inválido"),
    MDWACH_022(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-022", "Parámetros de tamaño incorrectos"),
    MDWACH_023(HttpStatus.BAD_REQUEST, "ERROR_ACH_ACCOUNT_NUMBER", "MDWACH-023", "Error en el nombre del esquema de la cuenta de crédito"),
    MDWACH_024(HttpStatus.BAD_REQUEST, "TARGET_ACCOUNT_KO", "MDWACH-024", "Identificación de cuenta del acreedor inválida"),
    MDWACH_025(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_001", "MDWACH-025", "Identificación secundaria inválida"),
    MDWACH_026(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_002", "MDWACH-026", "Identificación del documento inválida"),
    MDWACH_027(HttpStatus.BAD_REQUEST, "INVALID_IDENTIFICATION_003", "MDWACH-027", "Nombre inválido"),
    MDWACH_028(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-028", "Tamaño de los parámetros invalidos"),
    MDWACH_029(HttpStatus.BAD_REQUEST, "CRYPTO_KO", "MDWACH-029", "Transacción no procesada, validación de criptomonedas fallida"),
    MDWACH_030(HttpStatus.BAD_REQUEST, "TRANSFER_KO_002", "MDWACH-030", "Transacción no procesada, rol de usuario no válido"),
    MDWACH_031(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-031", "Transacción no procesada, error al insertar el registro principal"),
    MDWACH_032(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-032", "Transacción no procesada, error al insertar en autorización"),
    MDWACH_033(HttpStatus.BAD_REQUEST, "TRANSFER_KO_003", "MDWACH-033", "Transacción no procesada, error al verificar las firmas o poderes"),
    MDWACH_034(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-034", "Transacción no procesada, error al actualizar el registro principal"),
    MDWACH_035(HttpStatus.BAD_REQUEST, "LIMIT_KO_002", "MDWACH-035", "Cantidad límite excedida"),
    MDWACH_036(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-036", "Error al actualizar cantidad límite"),
    MDWACH_037(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-037", "Error al obtener número de transacción"),
    MDWACH_038(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-038", "Transacción no procesada, error al obtener el registro principal"),
    MDWACH_039(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-039", "Error en el procesamiento"),
    MDWACH_040(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-040", "Parámetros inválidos"),
    MDWACH_041(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWACH-041", "Canal inválido"),
    MDWACH_043(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWACH-043", "La cuenta no pertenece al usuario");
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
