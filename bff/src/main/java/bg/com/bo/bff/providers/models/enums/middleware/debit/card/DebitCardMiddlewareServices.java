package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareServices {
    CHANGE_AMOUNT("/bs/v1/debit-card/change-amount"),
    GET_lIST_INTERNET_AUTHORIZATION("/bs/v1/debit-card/list-internet-limit/%s/persons/%s");

    private final String serviceURL;
}
