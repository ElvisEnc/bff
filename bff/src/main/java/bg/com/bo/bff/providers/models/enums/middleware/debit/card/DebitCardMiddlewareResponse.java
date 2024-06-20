package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_CHANGE_AMOUNT("SUCCESS","Se modifico correctamente el límite de su tarjeta."),
    ERROR_CHANGE_AMOUNT("ERROR","No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_UPDATE_STATUS_LOCK("SUCCESS","Se modifico correctamente el estado de su tarjeta."),
    ERROR_UPDATE_STATUS_LOCK("ERROR","No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_MODIFY_ACCOUNTS_ORDER("SUCCESS","Se modifico correctamente el orden de las cuentas."),
    ERROR_MODIFY_ACCOUNTS_ORDER("ERROR","No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_DELETE_AUTH_PURCHASE("SUCCESS","Se elimino la compra por internet."),
    ERROR_DELETE_AUTH_PURCHASE("ERROR","No se pudo realizar la eliminación.");

    private final String code;
    private final String message;
}
