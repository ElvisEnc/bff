package bg.com.bo.bff.providers.models.enums.middleware.nps;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NpsMiddlewareError implements IMiddlewareError {
    RM001(
            HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "RM001",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    RM002(
            HttpStatus.CONFLICT, CodeError.DATA_NOT_FOUND.getCode(), "RM002",
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            CategoryError.MW_GENERIC_FAIL_RESPONSE.getCategoryId());

    private final org.springframework.http.HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String title;
    private final String message;
    private final int categoryId;
}
