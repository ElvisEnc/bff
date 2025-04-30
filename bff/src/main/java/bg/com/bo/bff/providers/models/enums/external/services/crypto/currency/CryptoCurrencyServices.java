package bg.com.bo.bff.providers.models.enums.external.services.crypto.currency;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoCurrencyServices {

    POST_REGISTER_ACCOUNT("/empresas/criptoactivos-mdw/api/v1/account/account-create");

    private final String serviceURL;
}
