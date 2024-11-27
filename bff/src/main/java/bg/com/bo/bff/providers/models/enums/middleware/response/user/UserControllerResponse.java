package bg.com.bo.bff.providers.models.enums.middleware.response.user;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserControllerResponse implements IGenericControllerResponse {
    SUCCESS("SUCCESS", "La contraseña se cambió exitosamente.", "Cambio contraseña");

    private final String code;
    private final String message;
    private final String title;
}
