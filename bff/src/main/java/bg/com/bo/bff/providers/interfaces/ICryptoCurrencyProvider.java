package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyPostRegisterAccountResponse;

import java.io.IOException;

public interface ICryptoCurrencyProvider {

    CryptoCurrencyPostRegisterAccountResponse registerAccount (CryptCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyGetAvailableBalanceResponse getAvailableBalance (CryptCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyGetAccountEmailResponse getAccountEmail (CryptCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyAccountExtractResponse getAccountExtract (CryptoCurrencyAccountExtractRequest requestServer) throws IOException;

    CryptoCurrencyExchangeRateResponse getExchangeRate (CryptoCurrencyExchangeRateRequest requestServer) throws IOException;
}
