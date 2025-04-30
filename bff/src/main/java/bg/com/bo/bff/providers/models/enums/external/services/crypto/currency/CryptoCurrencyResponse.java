package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyResponse implements IGenericControllerResponse {
    REGISTERED_EXIT("SUCCESS", "Suscripcion realizada correctamente.", "Suscripcion registrada"),
    REGISTRATION_EXISTS("REGISTERED", "La persona ya se encuentra inscrita a esta campaña", "Pesona ya registrada")
    ;

    private final String code;
    private final String message;
    private final String tittle;
}
