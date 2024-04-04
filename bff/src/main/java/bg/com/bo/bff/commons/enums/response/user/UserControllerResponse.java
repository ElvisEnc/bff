package bg.com.bo.bff.commons.enums.response.user;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserControllerResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS", "La contraseña se cambió exitosamente.");

    private final String code;
    private final String message;
}
