package bg.com.bo.bff.application.dtos.response.generic;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.providers.models.interfaces.middleware.IMiddlewareError;

@lombok.Data
@lombok.ToString
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class ErrorResponse {
    private String code;
    private String message;
    private String title;

    public static ErrorResponse instance(IMiddlewareError error) {
        return ErrorResponse.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .title(error.getTitle())
                .build();
    }

    public static ErrorResponse instance(GenericException e) {
        return ErrorResponse.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .title(e.getTitle())
                .build();
    }

    public static ErrorResponse instance(HandledException exception) {
        return ErrorResponse.builder()
                .code(String.valueOf(exception.getCode()))
                .message(exception.getMessage())
                .title(exception.getTitle())
                .build();
    }
}
