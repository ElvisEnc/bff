package bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OnboardingManagerMiddlewareError implements IMiddlewareError {
    RM001(
            HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(),
            "RM001", ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    RM002(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(),
            "RM002", ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    ERR002(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(),
            "ERR002", ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(), CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId()
    ),
    ONB_0001(
            HttpStatus.NOT_ACCEPTABLE, CodeError.ERROR_DB_PROCEDURE.getCode(),
            "ONB-0001", ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    DB_0005(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(),
            "DB-0005", ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()
    ),
    ONB_0002(
            HttpStatus.NOT_ACCEPTABLE, "SP_EXECUTION",
            "ONB-0002", ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    ),
    ONB_0003(
            HttpStatus.NOT_IMPLEMENTED, "NOT_URL_INVALID",
            "ONB-0003", "La URL proporcionada es inválida.",
            "URL inválida", CategoryError.INVALID_REQUEST_MW_ERROR.getCategoryId()
    ),
    ONB_0005(
            HttpStatus.NOT_IMPLEMENTED, CodeError.ERROR_DB_PROCEDURE.getCode(),
            "ONB-0005", ConstantMessages.GENERIC.getTitle(),
            ConstantMessages.GENERIC.getMessage(), CategoryError.MW_SPECIFIC_FAIL_RESPONSE.getCategoryId()
    );

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;

}
