package bg.com.bo.bff.providers.models.enums.middleware.debit.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DebitCardMiddlewareServices {
    CHANGE_AMOUNT("/bs/v1/debit-card/change-amount");

    private final String serviceURL;
}
