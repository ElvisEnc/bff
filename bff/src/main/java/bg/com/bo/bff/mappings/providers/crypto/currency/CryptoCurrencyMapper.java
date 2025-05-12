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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CryptoCurrencyMapper implements ICryptoCurrencyMapper{

    @Override
    public AvailableBalanceResponse convertResponse(CryptoCurrencyGetAvailableBalanceResponse response) {
        return AvailableBalanceResponse.builder()
                .currency(response.getData().getCurrency())
                .availableBalance(response.getData().getAvailableBalance())
                .account(response.getData().getAccount())
                .status(response.getData().getStatus())
                .build();
    }

    @Override
    public AccountEmailResponse convertResponse(CryptoCurrencyGetAccountEmailResponse response) {
        return AccountEmailResponse.builder()
                .email(response.getData().getEmail())
                .build();
    }

    @Override
    public List<AccountExtractResponse> convertResponse(CryptoCurrencyAccountExtractResponse response) {
        if (response == null || response.getData() == null) {
            return List.of();
        }
        return response.getData().stream()
                .map(item -> AccountExtractResponse.builder()
                        .existsVoucher(Boolean.parseBoolean(item.getExistsVoucher()))
                        .transactionDate(item.getTransactionDate())
                        .transactionTime(item.getTransactionTime())
                        .amount(item.getAmount())
                        .description(item.getDescription())
                        .day(item.getDay())
                        .month(item.getMonth())
                        .year(item.getYear())
                        .transactionType(item.getTransactionType())
                        .processDate(item.getProcessDate())
                        .branch(item.getBranch())
                        .seatNumber(item.getSeatNumber())
                        .correlative(item.getCorrelative())
                        .currentBalance(item.getCurrentBalance())
                        .currencySymbol(item.getCurrencySymbol())
                        .build())
                .toList();
    }

    @Override
    public ExchangeRateResponse convertResponse(CryptoCurrencyExchangeRateResponse response) {
        return null;
    }

    @Override
    public CryptCurrencyPersonRequest mapperRequest(String personId) {
        return CryptCurrencyPersonRequest.builder()
                .personId(personId)
                .build();
    }

    @Override
    public CryptoCurrencyAccountExtractRequest mapperRequest(String accountId, AccountExtractRequest request) {
        return CryptoCurrencyAccountExtractRequest.builder()
                .jtsOidNumber(Integer.valueOf(accountId))
                .accountNumber(request.getAccountNumber())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .startPage(request.getStartPage())
                .endPage(request.getEndPage())
                .build();
    }

    @Override
    public CryptoCurrencyExchangeRateRequest mapperRequest(String personId, String currencyId) {
        return CryptoCurrencyExchangeRateRequest.builder()
                .personNumber(Integer.valueOf(personId))
                .currencyCode(Integer.valueOf(currencyId))
                .build();
    }
}
