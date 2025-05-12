package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountEmailResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AccountExtractResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.AvailableBalanceResponse;
import bg.com.bo.bff.application.dtos.response.crypto.currency.ExchangeRateResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

import java.io.IOException;
import java.util.List;

public interface ICryptoCurrencyService {

    GenericResponse registerAccount (String personId) throws IOException;

    AvailableBalanceResponse getAvailableBalance (String personId) throws IOException;

    AccountEmailResponse getAccountEmail (String personId) throws IOException;

    List<AccountExtractResponse> getAccountExtract (String personId, String accountId, AccountExtractRequest request) throws IOException;

    ExchangeRateResponse getExchangeRate (String personId, String currencyId) throws IOException;
}
