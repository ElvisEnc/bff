package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CreditCardMiddlewareError implements IMiddlewareError {
    MDWCCM_0001(
            HttpStatus.NOT_ACCEPTABLE, "NOT_ACCEPTABLE", "MDWCCM-0001",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            "No se encontraron datos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0003(
            HttpStatus.BAD_REQUEST, "BAD_REQUEST", "MDWCCM-0003",
            "Datos invalidos",
            "El dato proporcionado es incorrecto.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0004(
            HttpStatus.INTERNAL_SERVER_ERROR, "PROCESSING_ERROR", "MDWCCM-0004",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al obtener los datos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0005(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_EXECUTION_MDW", "MDWCCM-0005",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al obtener los datos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0006(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_CONNECTION", "MDWCCM-0006",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error en la conexión con Linkser.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0007(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_EXECUTION", "MDWCCM-0007",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al obtener los datos de Linkser.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0008(
            HttpStatus.BAD_REQUEST, "ERROR_LIMITS", "MDWCCM-0008",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al validar los límites.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0010(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_LINKSER_CONNECTION", "MDWCCM-0010",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al conectarse con Linkser.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_0015(
            HttpStatus.INTERNAL_SERVER_ERROR, "PROCESSING_ERROR", "MDWCCM-0015",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrió un error al procesar la comisión de la tarjeta.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWCCM_900(
            HttpStatus.INTERNAL_SERVER_ERROR, "ERROR_MDW_CONNECTION_BD", "MDWCCM-900",
            ConstantMessages.GENERIC.getTitle(),
            "Ocurrio un error al obtener los datos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPGL_400(
            HttpStatus.BAD_REQUEST, "INVALID_DATA", "MDWPGL-400",
            ConstantMessages.GENERIC.getTitle(),
            "Datos inválidos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;
}
