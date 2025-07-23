package bg.com.bo.bff.providers.models.enums.middleware.response;

import org.springframework.http.HttpStatus;

public interface IErrorControllerResponse {
    HttpStatus getHttpCode();

    String getCode();

    String getDescription();

    default String getTitle() {
        return "";
    }

    default int getCategoryId() {
        return 0;
    }
}
