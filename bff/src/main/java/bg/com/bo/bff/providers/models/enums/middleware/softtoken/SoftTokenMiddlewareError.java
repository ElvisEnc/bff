package bg.com.bo.bff.providers.models.enums.middleware.softtoken;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SoftTokenMiddlewareError implements IMiddlewareError {

    STM_003(HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_PROCEDURE.getCode(), "STM-003",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    PINDIG030(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG030",  "Debe seleccionar alguna opción de Envio.", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(),CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG001(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG001",  "Error al Validar Enrolamiento de GanaPin Digital.", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG032(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG032",  "Supero el limite de dispositivos habilitados, para habilitar este debes bloquear uno en GanaNet o en el Banco.", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG033(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG033",  "Solicitud de Afiliación a GanaPin Digital pendiente de activación en GanaNet", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG048(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG048",  "Error al actualizar Solicitud de Enrolamiento", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG049(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG049",  "Estado de Enrolamiento Desconocido", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    PINDIG041(HttpStatus.CONFLICT, CodeError.SOFTTOKEN_DISABLED.getCode(), "PINDIG041",  "Dispositivo se dio de Baja para el Uso de GanaPin Digital", ConstantMessages.SOFTTOKEN_DISABLED.getTitle(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
