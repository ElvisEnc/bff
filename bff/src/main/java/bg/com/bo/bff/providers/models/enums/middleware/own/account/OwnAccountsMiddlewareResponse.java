package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_UPDATE_TRANSACTION_LIMIT("SUCCESS", "Se actualizó correctamente el límite transaccional.","Límites diarios guardados"),
    ERROR_UPDATE_TRANSACTION_LIMIT("ERROR", "No se pudo realizar la actualización.","Ocurrio un problema");

    private final String code;
    private final String message;
    private final String title;
}
