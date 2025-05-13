package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.ExternalServiceConfig;
import bg.com.bo.bff.commons.enums.config.provider.external.ProjectName;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;

import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;

import bg.com.bo.bff.providers.models.external.services.interfaces.CryptoAssetsFeignClient;

import org.springframework.stereotype.Service;


@Service
public class CryptoCurrencyProvider implements ICryptoCurrencyProvider {
    private final CryptoAssetsFeignClient cryptoAssetsFeignClient;

    public CryptoCurrencyProvider(CryptoAssetsFeignClient cryptoAssetsFeignClient) {
        this.cryptoAssetsFeignClient = cryptoAssetsFeignClient;
    }

    @Override
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(
            String token, CryptCurrencyPersonRequest requestServer
    ) {
        // String url = baseUrl + String.format(CryptoCurrencyServices.POST_REGISTER_ACCOUNT.getServiceURL());
        return this.cryptoAssetsFeignClient.createAccount(token, requestServer);
    }

}
