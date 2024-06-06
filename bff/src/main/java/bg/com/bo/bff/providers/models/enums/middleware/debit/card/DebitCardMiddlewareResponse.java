package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_CHANGE_AMOUNT("SUCCESS","Se modifico correctamente el límite de su tarjeta."),
    ERROR_CHANGE_AMOUNT("ERROR","No se pudo realizar la modificación, intente más tarde.");

    private final String code;
    private final String message;
}
