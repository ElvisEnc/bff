package bg.com.bo.bff.providers.models.enums.middleware.credit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreditCardMiddlewareService {
    GET_LIST_CARD("/bs/v1/cards/persons/%s"),
    GET_DETAIL_CREDIT_CARD("/bs/v1/cards/persons/%s/creditcards/%s"),
    BLOCK_CREDIT_CARD("/bs/v1/cards/block"),
    GET_AVAILABLE_CREDIT_CARD("/bs/v1/cards/%s/available"),
    GET_PERIOD_CREDIT_CARD("/bs/v1/cards/payments/periods"),
    CASH_ADVANCE_FEE("/bs/v1/cards/advances/commission"),
    GET_LIST_CREDIT_CARD("/bs/v1/cards/?personId=%s&cmsAccountNumber=%s"),
    PAY_CREDIT_CARD("/bs/v1/payment/execute"),
    GET_DETAIL_PREPAID_CARD("/bs/v1/cards/persons/%s/prepaids/%s"),
    CASH_ADVANCE_CREDIT_CARD("/bs/v1/cards/advances/cash"),
    GET_MOVEMENTS("/bs/v1/cards/movements?cmsCardNumber=%s&initDate=%s&endDate=%s"),
    GET_AUTHS("/bs/v1/cards/%s/persons/%s/limits"),
    POST_AUTHORIZATION_CREDIT_CARD("/bs/v1/purchases/online"),
    GET_FEE_PREPAID_CARD("/bs/v1/cards/advances/commissions/prepaid");

    private final String serviceURL;
}
