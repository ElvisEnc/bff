package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CreditCardMiddlewareError implements IMiddlewareError {
    MDWCCM_0001(HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWCCM-0001", "No se encontraron datos"),
    MDWCCM_0003(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCM-0003", "El dato proporcionado es incorrecto"),
    MDWCCM_0004(HttpStatus.INTERNAL_SERVER_ERROR, "PROCESSING_ERROR", "MDWCCM-0004", "Ocurrio un error al obtener los datos"),
    MDWCCM_0005(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_EXECUTION_MDW", "MDWCCM-0005", "Ocurrio un error al obtener los datos"),
    MDWCCM_0006(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_CONNECTION", "MDWCCM-0006", "Ocurrio un error en la conexión con linkser"),
    MDWCCM_0007(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_EXECUTION", "MDWCCM-0007", "Ocurrio un error al obtener los datos de linkser"),
    MDWCCM_0008(HttpStatus.BAD_REQUEST, "ERROR_LIMITS", "MDWCCM-0008", "Ocurrio un error al validar los límites"),
    MDWCCM_0010(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_CONNECTION", "MDWCCM-0010", "Ocurrio un error al conectarse con linkser"),
    MDWCCM_0015(HttpStatus.INTERNAL_SERVER_ERROR, "PROCESSING_ERROR", "MDWCCM-0015", "Ocurrio un error al procesar la comisión de la tarjeta"),
    MDWCCM_900(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_MDW_CONNECTION_BD", "MDWCCM-900", "Ocurrio un error al obtener los datos"),
    MDWPGL_400(HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400", "Datos inválidos.");
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
}
