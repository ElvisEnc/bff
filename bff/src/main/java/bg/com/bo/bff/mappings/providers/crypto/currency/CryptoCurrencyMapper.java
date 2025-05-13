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
import bg.com.bo.bff.commons.enums.config.provider.CanalMW;
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
        return ExchangeRateResponse.builder()
                .purchaseFxRate(response.getData().getPurchaseFxRate())
                .saleFxRate(response.getData().getSaleFxRate())
                .description(response.getData().getDescription())
                .build();
    }

    @Override
    public ExchangeOperationResponse convertResponse(CryptoCurrencyExchangeOperationResponse response) {
        return ExchangeOperationResponse.builder()
                .build();
    }

    @Override
    public GenerateVoucherResponse convertResponse(CryptoCurrencyGenerateVoucherResponse response) {
        return GenerateVoucherResponse.builder()
                .transactionNumber(response.getData().getTransactionNumber())
                .transactionType(response.getData().getTransactionType())
                .build();
    }

    @Override
    public CryptoCurrencyPersonRequest mapperRequest(String personId) {
        return CryptoCurrencyPersonRequest.builder()
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

    @Override
    public CryptoCurrencyExchangeOperationRequest mapperRequest(String personId, String accountId, ExchangeOperationRequest request) {
        return CryptoCurrencyExchangeOperationRequest.builder()
                .amount(request.getAmount())
                .currencyCode(Integer.valueOf(request.getCurrency()))
                .accountId(Integer.valueOf(accountId))
                .destinationAccount(Integer.valueOf(request.getDestinationAccount()))
                .description(request.getDescription())
                .requestNumber(0)
                .canal(Integer.parseInt(CanalMW.GANAMOVIL.getCanal()))
                .build();
    }

    @Override
    public CryptoCurrencyGenerateVoucherRequest mapperRequest(GenerateVoucherRequest request) {
        return CryptoCurrencyGenerateVoucherRequest.builder()
                .seatNumber(Long.valueOf(request.getSeatNumber()))
                .branch(0)
                .dateProcess(request.getDateProcess())
                .build();
    }
}
