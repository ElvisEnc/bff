package bg.com.bo.bff.providers.models.enums.middleware.remittance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RemittanceMiddlewareService {
    GET_GENERAL_PARAMETERS("/bs/v1/general-parameters"),
    GET_MONEY_ORDERS_SENT("/bs/v1/get-money-order-sent"),
    VALIDATE_ACCOUNT("/bs/v1/validate-account"),
    CHECK_CUSTOMER_REMITTANCE("/bs/v1/consult-customer-remittance"),
    DEPOSIT_REMITTANCE("/bs/v1/perform-deposit"),
    DEPOSIT_REMITTANCE_WU("/bs/v1/perform-deposit-wu"),
    CONSULT_CUSTOMER_REMITTANCE_WU("/bs/v1/consult-customer-remittance-wu"),
    UPDATE_WESTERUNION_REMITTANCE("/bs/v1/update-wu");

    private final String serviceURL;
}
