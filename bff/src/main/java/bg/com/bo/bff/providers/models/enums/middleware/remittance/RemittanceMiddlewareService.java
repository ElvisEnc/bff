package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareService {
    GET_GENERAL_PARAMETERS("/bs/v1/general-parameters"),
    GET_MONEY_ORDERS_SENT("/bs/v1/get-money-order-sent"),
    VALIDATE_ACCOUNT("/bs/v1/validate-account");

    private final String serviceURL;
}
