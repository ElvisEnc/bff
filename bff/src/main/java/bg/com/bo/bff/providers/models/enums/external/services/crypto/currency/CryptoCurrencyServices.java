package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyServices {

    AUTHENTICATION("/api/v1/authentication"),
    POST_REGISTER_ACCOUNT("/api/v1/account/account-create"),
    POST_AVAILABLE_BALANCE("/api/v1/account/available-balance"),
    POST_ACCOUNT_EMAIL("/api/v1/account/account-email"),
    ;

    private final String serviceURL;
}
