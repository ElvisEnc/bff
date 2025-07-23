package bg.com.bo.bff.providers.models.enums.middleware;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenericControllerResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS", "Success");

    private final String code;
    private final String message;
}
