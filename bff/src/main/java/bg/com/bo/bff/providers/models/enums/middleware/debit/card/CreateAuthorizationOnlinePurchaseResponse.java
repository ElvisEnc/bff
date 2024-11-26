package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreateAuthorizationOnlinePurchaseResponse implements IGenericControllerResponse {
    SUCCESS_CREATE("SUCCESS","Se creó correctamente la autorización de compras por internet."),
    ERROR_CREATE("ERROR","No se pudo crear la autorización de compras por internet, intente más tarde.");
    private final String code;
    private final String message;
}
