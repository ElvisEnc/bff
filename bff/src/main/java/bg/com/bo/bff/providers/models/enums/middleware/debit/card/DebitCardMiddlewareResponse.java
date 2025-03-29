package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import bg.com.bo.bff.commons.enums.ConstantMessages;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareResponse implements IGenericControllerResponse {

    SUCCESS_CHANGE_AMOUNT(ConstantMessages.SUCCESS.getTitle(), "Límites diarios guardados","Se modificó correctamente el límite de su tarjeta."),
    ERROR_CHANGE_AMOUNT(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),ConstantMessages.MODIFY_ERROR.getMessage()),
    SUCCESS_UPDATE_STATUS_LOCK(ConstantMessages.SUCCESS.getTitle(), "Tarjeta reactivada","Ahora puedes usar tu tarjeta nuevamente para compras y retiros."),
    ERROR_UPDATE_STATUS_LOCK(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),ConstantMessages.MODIFY_ERROR.getMessage()),
    SUCCESS_MODIFY_ACCOUNTS_ORDER(ConstantMessages.SUCCESS.getTitle(), "Cuenta asociada actualizada","Has cambiado la cuenta vinculada a tu tarjeta de débito. A partir de ahora, todas las transacciones se debitarán de la nueva cuenta seleccionada."),
    ERROR_MODIFY_ACCOUNTS_ORDER(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),ConstantMessages.MODIFY_ERROR.getMessage()),
    SUCCESS_DELETE_AUTH_PURCHASE(ConstantMessages.SUCCESS.getTitle(), "Autorización eliminada","Se elimino la compra por internet."),
    ERROR_DELETE_AUTH_PURCHASE(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),"No se pudo realizar la eliminación."),
    SUCCESS_ACTIVE_ASSURANCE(ConstantMessages.SUCCESS.getTitle(), "Seguro activo","La Tarjeta de Débito ya está protegida contra estafas, fraudes o usos indebidos."),
    ERROR_ACTIVE_ASSURANCE(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),"No se pudo activar el seguro de la tarjeta de débito."),
    SUCCESS_ACTIVATE_DEBIT_CARD(ConstantMessages.SUCCESS.getTitle(), "Tarjeta activada","Ya puedes usar tu tarjeta para compras y retiros."),
    ERROR_ACTIVATE_DEBIT_CARD(ConstantMessages.ERROR.getTitle(),ConstantMessages.GENERIC.getTitle(),"Ocurrio algo inesperado, no se pudo activar la tarjeta de debito."),
    SUCCESS_CHANGE_PIN_CARD(ConstantMessages.SUCCESS.getTitle(), "Nuevo PIN guardado","Enviamos un mail con la confirmación del cambio."),
    ERROR_CHANGE_PIN_CARD(ConstantMessages.ERROR.getTitle(), ConstantMessages.GENERIC.getTitle(),"Ocurrió algo inesperado, no se pudo cambiar el pin de la tarjeta de débito.");

    private final String code;
    private final String title;
    private final String message;

}
