package bg.com.bo.bff.mappings.providers.crypto.currency;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeOperationResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.GenerateVoucherResponse;
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

import java.util.List;

public interface ICryptoCurrencyMapper {

    AvailableBalanceResponse convertResponse(CryptoCurrencyGetAvailableBalanceResponse response);

    AccountEmailResponse convertResponse(CryptoCurrencyGetAccountEmailResponse response);

    List<AccountExtractResponse> convertResponse(CryptoCurrencyAccountExtractResponse response);

    ExchangeRateResponse convertResponse(CryptoCurrencyExchangeRateResponse response);

    ExchangeOperationResponse convertResponse(CryptoCurrencyExchangeOperationResponse response);

    GenerateVoucherResponse convertResponse(CryptoCurrencyGenerateVoucherResponse response);

    CryptoCurrencyPersonRequest mapperRequest(String personId);

    CryptoCurrencyAccountExtractRequest mapperRequest(String accountId, AccountExtractRequest request);

    CryptoCurrencyExchangeRateRequest mapperRequest(String personId, String currencyId);

    CryptoCurrencyExchangeOperationRequest mapperRequest(String personId, String accountId, ExchangeOperationRequest request);

    CryptoCurrencyGenerateVoucherRequest mapperRequest(GenerateVoucherRequest request);
}
