package bg.com.bo.bff.providers.models.enums.middleware.third.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ThirdAccountMiddlewareServices  {
    VALIDATE_NEW_ACCOUNT("/bs/v1/%s/party-accounts/%s"),
    ADD_THIRD_ACCOUNT("/bs/v1/third-party-accounts"),
    ADD_WALLET_ACCOUNT("/bs/v1/wallets"),
    DELETE_THIRD_ACCOUNT("/bs/v1/third-party-accounts"),
    DELETE_WALLET_ACCOUNT("/bs/v1/wallets/%s/persons/%s/wallet-accounts/%s"),
    GET_THIRD_ACCOUNTS("/bs/v1/persons/%s/companies/%s"),
    GET_WALLET_ACCOUNTS("/bs/v1/wallets/companies/%s/persons/%s");

    private final String serviceURL;
}
