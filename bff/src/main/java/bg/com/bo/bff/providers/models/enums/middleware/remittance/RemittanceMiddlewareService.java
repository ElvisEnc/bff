package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareService {
    GET_GENERAL_PARAMETERS("/bs/v1/general-parameters");

    private final String serviceURL;
}
