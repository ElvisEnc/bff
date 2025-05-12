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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoCurrencyProvider extends HttpClientExternalProvider<CryptoCurrencyError> implements ICryptoCurrencyProvider {
    private final String baseUrl;

    public CryptoCurrencyProvider(IHttpClientFactory httpClientFactory, ObjectMapper objectMapper, ExternalServiceConfig externalConfig) {
        super(httpClientFactory, objectMapper, CryptoCurrencyError.class);
        this.baseUrl = externalConfig.getUrlExternalBase() + ProjectName.CRYPTOCURRENCY_MANAGER.getName();
    }

    @Override
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(CryptCurrencyPersonRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_REGISTER_ACCOUNT.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyGetAvailableBalanceResponse getAvailableBalance(CryptCurrencyPersonRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_AVAILABLE_BALANCE.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyGetAccountEmailResponse getAccountEmail(CryptCurrencyPersonRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_ACCOUNT_EMAIL.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyAccountExtractResponse getAccountExtract(CryptoCurrencyAccountExtractRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_ACCOUNT_EXTRACT.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyExchangeRateResponse getExchangeRate(CryptoCurrencyExchangeRateRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_EXCHANGE_RATE.getServiceURL());
        return null;
    }
}
