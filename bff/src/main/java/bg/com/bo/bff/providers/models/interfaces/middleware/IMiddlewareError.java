package bg.com.bo.bff.providers.models.interfaces.middleware;

import org.springframework.http.HttpStatus;

public interface IMiddlewareError {
    HttpStatus getHttpCode();

    String getCode();

    String getCodeMiddleware();

    String getMessage();

    default String getTitle() {
        return "";
    }

    default int getCategoryId() {
        return 0;
    }
}
