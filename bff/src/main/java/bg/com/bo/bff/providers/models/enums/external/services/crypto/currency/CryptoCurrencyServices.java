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
    POST_ACCOUNT_EXTRACT("/api/v1/divisa-exchange/account-extract"),
    POST_EXCHANGE_RATE("/api/v1/divisa-exchange/exchange-rate"),
    POST_EXCHANGE_OPERATION("/api/v1/divisa-exchange/exchange-operation"),
    POST_GENERATE_VOUCHER("/api/v1/divisa-exchange/generate-voucher"),
    ;

    private final String serviceURL;
}
