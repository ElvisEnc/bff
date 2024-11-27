package bg.com.bo.bff.providers.models.enums.middleware.login;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_LOGOUT("SUCCESS", "Cerro sesión satisfactoriamente.","Cerrar sesión");

    private final String code;
    private final String message;
    private final String title;
}
