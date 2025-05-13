package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.ExternalServiceConfig;
import bg.com.bo.bff.commons.enums.config.provider.external.ProjectName;
import bg.com.bo.bff.commons.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeOperationRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyGenerateVoucherRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeOperationResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGenerateVoucherResponse;
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
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(CryptoCurrencyPersonRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_REGISTER_ACCOUNT.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyGetAvailableBalanceResponse getAvailableBalance(CryptoCurrencyPersonRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_AVAILABLE_BALANCE.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyGetAccountEmailResponse getAccountEmail(CryptoCurrencyPersonRequest requestServer) throws IOException {
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

    @Override
    public CryptoCurrencyExchangeOperationResponse exchangeOperation(CryptoCurrencyExchangeOperationRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_EXCHANGE_OPERATION.getServiceURL());
        return null;
    }

    @Override
    public CryptoCurrencyGenerateVoucherResponse postGenerateVoucher(CryptoCurrencyGenerateVoucherRequest requestServer) throws IOException {
        String url = baseUrl + String.format(CryptoCurrencyServices.POST_GENERATE_VOUCHER.getServiceURL());
        return null;
    }
}
