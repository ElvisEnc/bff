package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyResponse implements IGenericControllerResponse {
    REGISTERED_SUCCESS("SUCCESS", "¡Apertura de cuenta exitosa!", "Cuenta creada exitosametne"),
    ;

    private final String code;
    private final String message;
    private final String tittle;
}
