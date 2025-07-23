package bg.com.bo.bff.providers.models.enums.middleware.own.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OwnAccountsMiddlewareService {

    GET_OWN_ACCOUNTS("/bs/v1/accounts/persons/%s/companies/%s/roles/%s"),
    GET_TRANSACTION_LIMIT("/bs/v1/accounts/limits/persons/%s/accounts/%s/companies/%s"),
    PUT_TRANSACTION_LIMIT("/bs/v1/accounts/%s/persons/%s/companies/%s/limits");

    private final String serviceURL;
}
