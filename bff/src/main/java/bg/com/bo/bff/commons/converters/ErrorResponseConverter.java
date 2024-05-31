package bg.com.bo.bff.commons.converters;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.ErrorDetailResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

public abstract class ErrorResponseConverter {

    protected ErrorResponseConverter() {
    }

    @lombok.Getter
    @lombok.AllArgsConstructor
    public enum GenericErrorResponse implements IErrorResponse {
        DEFAULT(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", null, "Internal server error.");

        private final HttpStatus httpCode;
        private final String code;
        private final String mwCode;
        private final String message;
    }

    protected abstract IErrorResponse[] getValues();

    public final IErrorResponse convert(String response) throws IOException {
        ApiErrorResponse apiError = Util.stringToObject(response, ApiErrorResponse.class);
        List<ErrorDetailResponse> listError = apiError.getErrorDetailResponse();
        ErrorDetailResponse errorDetail = listError.get(0);
        String providerErrorCode = errorDetail.getCode();

        for (IErrorResponse errorResponse : this.getValues())
            if (errorResponse.getMwCode().equals(providerErrorCode))
                return errorResponse;
        return GenericErrorResponse.DEFAULT;
    }
}