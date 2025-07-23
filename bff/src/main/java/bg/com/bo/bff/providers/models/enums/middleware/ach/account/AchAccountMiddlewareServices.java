package bg.com.bo.bff.providers.models.enums.middleware.ach.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AchAccountMiddlewareServices {
    ADD_ACCOUNT("/bs/v1/ach"),
    DELETE_ACCOUNT("/bs/v1/ach/delete/ach-account/%s/persons/%s/accounts/%s"),
    GET_BANKS("/bs/v1/ach/others-bank-list"),
    GET_BRANCH_OFFICES("/bs/v1/ach/branch-offices/companies/%s"),
    GET_ACH_ACCOUNTS("/bs/v1/ach/ach-accounts/persons/%s/companies/%s"),
    GET_TRANSACTION_HISTORY("/bs/v1/ach/transactions-qr");
    private final String serviceURL;
}
