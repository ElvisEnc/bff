package bg.com.bo.bff.providers.models.enums.external.services.loyalty;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoyaltyResponse implements IGenericControllerResponse {

    REGISTERED_EXIT("SUCCESS", "Suscripcion realizada correctamente.", "Suscripcion registrada"),
    REGISTRATION_EXISTS("REGISTERED", "La persona ya se encuentra inscrita a esta campaña", "Pesona ya registrada"),
    EMAIL_EXISTS("EMAIL_REGISTERED", "El email ya se encuentra registrado.", "Email ya registrada"),
    INCOMPLETE_POINTS("INCOMPLETE_POINTS", "Los puntos actuales son inferiores a los del vale.", "Puntos incompletos"),
    INACTIVE_USER("INACTIVE_USER", "El vale no puede ser canjeado porque el usuario no se encuentra activo.", "Usuario inactivo"),
    VOUCHER_NOT_AVAILABLE("VOUCHER_NOT_AVAILABLE", "El vale ya no esta disponible para realizar canjes", "Vale no disponible"),
    SUBSCRIPTION_EXISTS("SUBSCRIPTION_EXISTS", "La persona se encuentra inscrita a esta campaña", "Pesona registrada"),
    VALIDATE_PROGRAM("VALIDATE_PROGRAM", "La persona pertenece al programa antiguo VAMOS.", "Flujo validado correctamente")
    ;

    private final String code;
    private final String message;
    private final String tittle;
}