package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareServices {
    CHANGE_AMOUNT("/bs/v1/debit-card/change-amount"),
    DETAIL("/bs/v1/debit-card/card?personId=%s&pciId=%s"),
    LIST_DEBIT_CARD("/bs/v1/debit-card/persons/"),
    ACCOUNT_LIST_DEBIT_CARD("/bs/v1/debit-card/associated-accounts/%d/persons/%d"),
    GET_lIST_INTERNET_AUTHORIZATION("/bs/v1/debit-card/list-internet-limit/%s/persons/%s"),
    UPDATE_LOCK_STATUS("/bs/v1/debit-card/update-lock-status"),
    CREATE_AUTHORIZATION_ONLINE_PURCHASE("/bs/v1/debit-card/register-limit-internet");
    private final String serviceURL;
}
