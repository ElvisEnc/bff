package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_ADD_ACCOUNT("SUCCESS", "Ahora podrás realizar transferencias a esta cuenta.","Cuenta destino guardada"),
    ERROR_ADD_ACCOUNT("ERROR_ACCOUNT", "Ocurrió un error inesperado al intentar añadir la cuenta.", "Error al agregar cuenta"),
    SUCCESS_DELETE_ACCOUNT("SUCCESS", "La cuenta ha sido eliminada exitosamente.", "Cuenta destino eliminada"),
    ERROR_ACCOUNT_NOT_FOUND("ERROR_FOUND", "No se encontró la cuenta solicitada. Verifica los datos e inténtalo nuevamente.", "Cuenta no encontrada"),
    ERROR_DELETE_ACCOUNT("ERROR_DELETE", "Ocurrió un error inesperado al intentar eliminar la cuenta.", "Error al eliminar cuenta");

    private final String code;
    private final String message;
    private final String title;
}
