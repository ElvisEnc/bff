package bg.com.bo.bff.providers.implementations;

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
import bg.com.bo.bff.providers.implementations.feign.CryptoCurrencyFeignClient;
import bg.com.bo.bff.providers.interfaces.ICryptoCurrencyProvider;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CryptoCurrencyProvider implements ICryptoCurrencyProvider {
    private final CryptoCurrencyFeignClient cryptoFeignClient;

    public CryptoCurrencyProvider(CryptoCurrencyFeignClient cryptoFeignClient) {
        this.cryptoFeignClient = cryptoFeignClient;
    }

    @Override
    public CryptoCurrencyPostRegisterAccountResponse registerAccount(
            String token, CryptoCurrencyPersonRequest requestServer
    ) {
        return this.cryptoFeignClient.createAccount(token, requestServer);
    }

    @Override
    public CryptoCurrencyGetAvailableBalanceResponse getAvailableBalance(CryptoCurrencyPersonRequest requestServer) throws IOException {
        return null;
    }

    @Override
    public CryptoCurrencyGetAccountEmailResponse getAccountEmail(CryptoCurrencyPersonRequest requestServer) throws IOException {
        return null;
    }

    @Override
    public CryptoCurrencyAccountExtractResponse getAccountExtract(CryptoCurrencyAccountExtractRequest requestServer) throws IOException {
        return null;
    }

    @Override
    public CryptoCurrencyExchangeRateResponse getExchangeRate(CryptoCurrencyExchangeRateRequest requestServer) throws IOException {
        return null;
    }

    @Override
    public CryptoCurrencyExchangeOperationResponse exchangeOperation(CryptoCurrencyExchangeOperationRequest requestServer) throws IOException {
        return null;
    }

    @Override
    public CryptoCurrencyGenerateVoucherResponse postGenerateVoucher(CryptoCurrencyGenerateVoucherRequest requestServer) throws IOException {
        return null;
    }
}
