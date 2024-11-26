package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreditCardMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_UPDATE_STATUS_LOCK("SUCCESS", "Se modifico correctamente el estado de su tarjeta."),
    ERROR_UPDATE_STATUS_LOCK("ERROR", "No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_AUTHORIZATION_ONLINE("SUCCESS", "Se realizo la autorizacion correctamente."),
    ERROR_AUTHORIZATION_ONLINE("ERROR", "No se pudo realizar la autorización, intente más tarde."),;

    private final String code;
    private final String message;
}
