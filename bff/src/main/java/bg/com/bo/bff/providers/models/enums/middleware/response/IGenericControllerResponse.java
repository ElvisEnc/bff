package bg.com.bo.bff.providers.models.enums.middleware.response;

public interface IGenericControllerResponse {
    String getCode();

    String getMessage();

    default String getTitle() {
        return "";
    }
}
