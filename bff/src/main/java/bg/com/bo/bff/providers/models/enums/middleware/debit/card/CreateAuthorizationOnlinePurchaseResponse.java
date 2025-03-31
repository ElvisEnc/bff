package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreateAuthorizationOnlinePurchaseResponse implements IGenericControllerResponse {
    SUCCESS_CREATE("SUCCESS", "Habilitación exitosa","Extendimos el monto extra para" +
            " compras por internet. Puedes volver a solicitar otra habilitación cuando lo necesites."),
    ERROR_CREATE("ERROR",  ConstantMessages.GENERIC.getTitle(),"No se pudo crear la autorización" +
            " de compras por internet, intente más tarde.");
    private final String code;
    private final String title;
    private final String message;
}
