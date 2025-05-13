package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.ExternalServiceConfig;
import bg.com.bo.bff.commons.enums.config.provider.external.ProjectName;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyError;
import bg.com.bo.bff.providers.models.enums.external.services.crypto.currency.CryptoCurrencyServices;
import bg.com.bo.bff.providers.models.external.services.HttpClientExternalProvider;
import bg.com.bo.bff.providers.models.external.services.interfaces.CryptoAssetsFeignClient;
//import bg.com.bo.bff.providers.models.external.services.interfaces.CryptoCurrencyFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoCurrencyProvider implements ICryptoCurrencyProvider {
    private final CryptoAssetsFeignClient cryptoAssetsFeignClient;
    private final String baseUrl;

    public CryptoCurrencyProvider(CryptoAssetsFeignClient cryptoAssetsFeignClient, ExternalServiceConfig externalConfig) {
        this.cryptoAssetsFeignClient = cryptoAssetsFeignClient;
        this.baseUrl = externalConfig.getUrlExternalBase() + ProjectName.CRYPTOCURRENCY_MANAGER.getName();
    }

    @Override
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(
            String token, CryptCurrencyPersonRequest requestServer
    ) {
        // String url = baseUrl + String.format(CryptoCurrencyServices.POST_REGISTER_ACCOUNT.getServiceURL());
        return this.cryptoAssetsFeignClient.createAccount(token, requestServer);
    }

}
