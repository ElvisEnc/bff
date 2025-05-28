package bg.com.bo.bff.providers.models.enums.middleware.notification.config;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotificationConfigMiddlewareError implements IMiddlewareError {
    RM001(
            HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM001",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM002(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM002",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()),
    MDWPGL_404(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWPGL-404",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWNMG_009(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "MDWNMG-009",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWNMG_025(
            HttpStatus.NOT_ACCEPTABLE, CodeError.BAD_REQUEST.getCode(), "MDWNMG-025",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            "El dispositivo ya cuenta con la notificacion seleccionada.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWNMG_026(
            HttpStatus.NOT_ACCEPTABLE, CodeError.BAD_REQUEST.getCode(), "MDWNMG-026",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            "La configuración  que intenta habilitar no existe, por favor verificar los datos.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    MDWNMG_027(
            HttpStatus.NOT_ACCEPTABLE, CodeError.BAD_REQUEST.getCode(), "MDWNMG-027",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            "La configuración  que intenta deshabilitar ya se encuentra deshabilitada.",
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    );


    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;
}
