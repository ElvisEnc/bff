package bg.com.bo.bff.providers.models.enums.middleware.certifications;

import bg.com.bo.bff.commons.enums.CategoryError;
import bg.com.bo.bff.commons.enums.CodeError;
import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CertificationsMiddlewareError implements IMiddlewareError {

    MCDCERTMGR_0001(
            HttpStatus.CONFLICT,
            CodeError.DATA_NOT_FOUND.getCode(),
            "MCDCERTMGR-0001",
            ConstantMessages.NO_FOUND_DATA.getMessage(),
            ConstantMessages.NO_FOUND_DATA.getTitle(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),
    MCDCERTMGR_0002(
            HttpStatus.CONFLICT,
            CodeError.ERROR_DB_PROCEDURE.getCode(),
            "MCDCERTMGR-0002",
            ConstantMessages.GENERIC.getMessage(),
            ConstantMessages.GENERIC.getTitle(),
            CategoryError.MW_NO_DATA_FAIL_RESPONSE.getCategoryId()
    ),

    ;

    private final HttpStatus httpCode;
    private final String code;
    private final String codeMiddleware;
    private final String message;
    private final String title;
    private final int categoryId;
}
