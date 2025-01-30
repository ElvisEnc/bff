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
    PINDIG030(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "PINDIG030",  "Debe seleccionar alguna opción de envio.", "Error en los parámetros",CategoryError.INVALID_FORMAT.getCategoryId()),
    PINDIG001(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG001",  "Error al validar enrolamiento de GanaPin Digital.", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG032(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG032",  "Supero el límite de dispositivos hábilitados, para habilitar este debes bloquear uno en GanaNet o en el Banco.", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG033(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG033",  "Solicitud de afiliación a GanaPin Digital pendiente de activación en GanaNet", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG048(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG048",  "Error al actualizar solicitud de enrolamiento", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG049(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG049",  "Estado de enrolamiento desconocido", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG041(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG041",  "Dispositivo se dio de baja para el uso de GanaPin Digital", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    ERROR_CODE_UNKNOWN(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "STM-005",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ;
    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
