package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareResponse implements IGenericControllerResponse {
    SUCCESS_CHANGE_AMOUNT("SUCCESS","Límites diarios guardados","Se modificó correctamente el límite de su tarjeta."),
    ERROR_CHANGE_AMOUNT("ERROR", ConstantMessages.GENERIC.getTitle(),"No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_UPDATE_STATUS_LOCK("SUCCESS", "Tarjeta reactivada","Se modificó correctamente el estado de su tarjeta."),
    ERROR_UPDATE_STATUS_LOCK("ERROR", ConstantMessages.GENERIC.getTitle(),"No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_MODIFY_ACCOUNTS_ORDER("SUCCESS", "Cuenta asociada actualizada","Has cambiado la cuenta vinculada a tu tarjeta de débito. A partir de ahora, todas las transacciones se debitarán de la nueva cuenta seleccionada."),
    ERROR_MODIFY_ACCOUNTS_ORDER("ERROR", ConstantMessages.GENERIC.getTitle(),"No se pudo realizar la modificación, intente más tarde."),
    SUCCESS_DELETE_AUTH_PURCHASE("SUCCESS","Autorización eliminada","Se elimino la compra por internet."),
    ERROR_DELETE_AUTH_PURCHASE("ERROR", ConstantMessages.GENERIC.getTitle(),"No se pudo realizar la eliminación."),
    SUCCESS_ACTIVE_ASSURANCE("SUCCESS","Seguro activo","La Tarjeta de Débito ya está protegida contra estafas, fraudes o usos indebidos."),
    ERROR_ACTIVE_ASSURANCE("ERROR", ConstantMessages.GENERIC.getTitle(),"No se pudo activar el seguro de la tarjeta de débito."),
    SUCCESS_ACTIVATE_DEBIT_CARD("SUCCESS", "Tarjeta activada","Ya puedes usar tu tarjeta para compras y retiros."),
    ERROR_ACTIVATE_DEBIT_CARD("ERROR",ConstantMessages.GENERIC.getTitle(),"Ocurrio algo inesperado, no se pudo activar la tarjeta de debito."),
    SUCCESS_CHANGE_PIN_CARD("SUCCESS","Nuevo PIN guardado","Enviamos un mail con la confirmación del cambio."),
    ERROR_CHANGE_PIN_CARD("ERROR", ConstantMessages.GENERIC.getTitle(),"Ocurrió algo inesperado, no se pudo cambiar el pin de la tarjeta de débito.");

    private final String code;
    private final String title;
    private final String message;

}
