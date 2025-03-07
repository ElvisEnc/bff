package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareError implements IMiddlewareError {

    STM_003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "STM-003",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_CODE_UNKNOWN(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "STM-005",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    COD001(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD001",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    COD003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD003",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    SEG006(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "SEG006",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_VALIDATING(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD095", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_NOT_FOUND_ID(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD096", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_NOT_FOUND_ID_DEVICE(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD097", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ERROR_SESSION_IOS(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "COD098", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),

    PINDIG001(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG001",  "Ocurrió un problema al habilitar tu GanaPin.", "Habilitación de GanaPin", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG002(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG002",  "Tu GanaPin ha sido habilitado correctamente.", "Habilitación de GanaPin", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG003(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG003",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG005(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG005",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG006(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG006",  "No encontramos datos de destino para el envío de códigos de habilitación. Verifica la información registrada e inténtalo nuevamente.", "Datos de destino no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG007(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG007",  "No encontramos datos de destino para el envío de códigos de habilitación. Verifica la información registrada e inténtalo nuevamente.", "Datos de destino no encontrados", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG008(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG008",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG010(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG010",  "Verifica el código ingresado y vuelve a intentarlo.", "Código incorrecto", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG011(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG011",  "Solicita un código nuevo y vuelve a intentarlo.", "Código expirado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG012(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG012",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG013(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG013",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG014(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG014",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG015(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG015",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG016(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG016",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG017(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG017",  "La respuesta a la pregunta de seguridad es incorrecta. Intenta nuevamente", "Respuesta incorrecta", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG020(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG020",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG021(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG021",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG022(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG022",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG023(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG023",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG024(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG024",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG025(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG025",  "y", "y", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG026(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG026",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG027(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG027",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG030(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "PINDIG030",  "Debe seleccionar alguna opción de envio.", "Error en los parámetros",CategoryError.INVALID_FORMAT.getCategoryId()),
    PINDIG032(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG032",  "Has superado el límite de dispositivos habilitados. Para habilitar este dispositivo, deberás bloquear uno desde GanaNet o en el Banco.", "Límite de dispositivos alcanzado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG033(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG033",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG037(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG037",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG038(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG038",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG039(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG039",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG040(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG040",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG041(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG041",  "Este dispositivo ha sido deshabilitado para utilizar GanaPin Digital. Si necesitas asistencia, por favor contacta con soporte.", "Dispositivo deshabilitado", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG044(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG044",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG048(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG048",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG049(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG049",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG050(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG050",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG051(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG051",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG052(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG052",  "y", "y", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG053(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG053",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG055(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG055",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG059(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG059",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG060(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG060",  "x", "x", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG062(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG062",  "y", "y", CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    ;
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
