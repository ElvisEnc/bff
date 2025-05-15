package bg.com.bo.bff.providers.interfaces;

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

import java.io.IOException;

public interface ICryptoCurrencyProvider {

    CryptoCurrencyPostRegisterAccountResponse registerAccount (String token,CryptoCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyGetAvailableBalanceResponse getAvailableBalance (CryptoCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyGetAccountEmailResponse getAccountEmail (CryptoCurrencyPersonRequest requestServer) throws IOException;

    CryptoCurrencyAccountExtractResponse getAccountExtract (CryptoCurrencyAccountExtractRequest requestServer) throws IOException;

    CryptoCurrencyExchangeRateResponse getExchangeRate (CryptoCurrencyExchangeRateRequest requestServer) throws IOException;

    CryptoCurrencyExchangeOperationResponse exchangeOperation (CryptoCurrencyExchangeOperationRequest requestServer) throws IOException;

    CryptoCurrencyGenerateVoucherResponse postGenerateVoucher (CryptoCurrencyGenerateVoucherRequest requestServer) throws IOException;
}
