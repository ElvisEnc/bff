package bg.com.bo.bff.services.implementations.v1;

import org.springframework.http.HttpStatus;

public interface ErrorControllerResponse {
    HttpStatus getHttpCode();
    String getCode();
    String getDescription();
}
