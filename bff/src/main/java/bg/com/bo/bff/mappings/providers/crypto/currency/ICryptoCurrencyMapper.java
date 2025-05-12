package bg.com.bo.bff.mappings.providers.crypto.currency;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptCurrencyPersonRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyAccountExtractRequest;
import bg.com.bo.bff.providers.dtos.request.crypto.currency.CryptoCurrencyExchangeRateRequest;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyAccountExtractResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyExchangeRateResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAccountEmailResponse;
import bg.com.bo.bff.providers.dtos.response.crypto.currency.CryptoCurrencyGetAvailableBalanceResponse;

import java.util.List;

public interface ICryptoCurrencyMapper {

    AvailableBalanceResponse convertResponse(CryptoCurrencyGetAvailableBalanceResponse response);

    AccountEmailResponse convertResponse(CryptoCurrencyGetAccountEmailResponse response);

    List<AccountExtractResponse> convertResponse(CryptoCurrencyAccountExtractResponse response);

    ExchangeRateResponse convertResponse(CryptoCurrencyExchangeRateResponse response);

    CryptCurrencyPersonRequest mapperRequest(String personId);

    CryptoCurrencyAccountExtractRequest mapperRequest(String accountId, AccountExtractRequest request);

    CryptoCurrencyExchangeRateRequest mapperRequest(String personId, String currencyId);
}
