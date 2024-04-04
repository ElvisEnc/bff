package bg.com.bo.bff.commons.enums.response;

import org.springframework.http.HttpStatus;

public interface IErrorControllerResponse {
    HttpStatus getHttpCode();
    String getCode();
    String getDescription();
}
