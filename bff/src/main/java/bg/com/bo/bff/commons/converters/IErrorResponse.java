package bg.com.bo.bff.commons.converters;

import org.springframework.http.HttpStatus;

public interface IErrorResponse {
    HttpStatus getHttpCode();

    String getCode();

    String getMwCode();

    String getMessage();

    default String getTitle() {
        return "";
    }
}
