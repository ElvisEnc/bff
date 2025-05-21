package bg.com.bo.bff.providers.models.external.services.interfaces;

import org.springframework.http.HttpStatus;

public interface IExternalError {
    HttpStatus getHttpCode();

    String getCode();

    String getMsgError();

    String getMessage();

    default String getTitle() {
        return "";
    }

    default int getCategoryId() {
        return 0;
    }
}
