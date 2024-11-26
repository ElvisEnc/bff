package bg.com.bo.bff.providers.models.enums.middleware.qr;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QRMiddlewareError implements IMiddlewareError {
    QR_EXPIRED(HttpStatus.BAD_REQUEST, "QR_EXPIRED", "QR_EXPIRED", "Solicita un nuevo QR con fecha actualizada e intenta nuevamente.", "Código QR vencido"),
    MDWGQM_001(HttpStatus.INTERNAL_SERVER_ERROR, "GENERATE_QR_IMAGE", "MDWGQM-001", "Tuvimos un problema al generar la imagen QR. Inténtalo nuevamente.", "Error al crear un QR"),
    MDWGQM_002(HttpStatus.INTERNAL_SERVER_ERROR, "GENERATE_QR_IMAGE_BASE_64", "MDWGQM-002", "Tuvimos un problema al generar la imagen QR. Inténtalo nuevamente.", "Error al crear un QR"),
    MDWGQM_003(HttpStatus.INTERNAL_SERVER_ERROR, "DECODE_IMAGE_BASE_64", "MDWGQM-003", "Tuvimos un problema al leer el código QR. Inténtalo nuevamente.", "Error al leer la imagen"),
    MDWGQM_012(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_ENCRYPT", "MDWGQM-012", "Tuvimos un problema al generar la imagen QR. Inténtalo nuevamente.", "Error al crear un QR"),
    MDWGQM_015(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DECRYPT", "MDWGQM-015", "Tuvimos un problema al leer el código QR. Inténtalo nuevamente.", "Error de lectura de QR"),
    MDWGQM_007(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_DATA_ACCOUNT_ORIGIN", "MDWGQM-007", "Tuvimos un problema al generar la imagen QR. Inténtalo nuevamente.", "Error al crear un QR"),
    MDWGQM_009(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "MDWGQM-009", "Tuvimos un problema interno. Inténtalo nuevamente.", "Ocurrió un problema"),
    MDWGQM_010(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWGQM-010", "Error en los parámetros proporcionados.", "Error en parámetros"),
    MDWGQM_013(HttpStatus.BAD_REQUEST, "ACCOUNT_CURRENCY_ORIGIN_DIFFERENT", "MDWGQM-013", "La moneda de la cuenta origen es diferente.", "Moneda de cuenta origen diferente"),
    MDWGQM_014(HttpStatus.BAD_REQUEST, "FORMAT_IS_NOT_VALID_ENTITY", "MDWGQM-014", "No pudimos leer el código QR. Intenta cargar la imagen nuevamente.", "Código QR inválido"),
    MDWGQM_011(HttpStatus.BAD_REQUEST, "ERROR_DATE", "MDWGQM-011", "La fecha proporcionada no es correcta.", "Fecha incorrecta"),
    MDWGQM_004(HttpStatus.BAD_REQUEST, "CONVERTING_DATE_FORMAT", "MDWGQM-004", "El formato de la fecha no es correcto.", "Formato de fecha incorrecto"),
    MDWGQM_008(HttpStatus.BAD_REQUEST, "SOURCE_ACCOUNT_KO", "MDWGQM-008", "No es posible utilizar esta cuenta para realizar esta transacción.", "Cuenta de origen inactiva"),
    MDWGQM_017(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWGQM-017", "La cuenta o el código de persona no pertenece.", "Cuenta o código de persona incorrecto");

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
}
