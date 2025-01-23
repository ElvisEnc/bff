package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareResponse implements IGenericControllerResponse {
    ACCOUNT_ENABLED("SUCCESS","La cuenta esta disponible","Cuenta habilitada");

    private final String code;
    private final String message;
    private final String title;
}
