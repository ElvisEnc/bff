package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_ADD_ACCOUNT("SUCCESS", "Se agregó la cuenta correctamente"),
    ERROR_ADD_ACCOUNT("ERROR", "Ocurrio algo inesperado al agregar la cuenta"),
    SUCCESS_DELETE_ACCOUNT("SUCCESS", "Se eliminó la cuenta correctamente"),
    ERROR_DELETE_ACCOUNT("ERROR", "Ocurrio algo inesperado al eliminar la cuenta");

    private final String code;
    private final String message;
}
