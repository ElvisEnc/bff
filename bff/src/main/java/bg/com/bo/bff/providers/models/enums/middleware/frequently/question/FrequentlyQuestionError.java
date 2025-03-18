package bg.com.bo.bff.providers.models.enums.middleware.frequently.question;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FrequentlyQuestionError implements IMiddlewareError {
    UQM_0001(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "UQM-0001",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    UQM_0005(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "UQM-0005",  ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    UQM_0006(HttpStatus.NOT_ACCEPTABLE, CodeError.DATA_NOT_FOUND.getCode(), "UQM-0006", ConstantMessages.GENERIC.getMessage(), ConstantMessages.GENERIC.getTitle(), CategoryError.MW_GENERIC_INTERNAL_FAILURE.getCategoryId()),
    ;


    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
