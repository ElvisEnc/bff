package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.request.transfer.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.commons.utils.UtilDate;
import bg.com.bo.bff.providers.dtos.request.transfer.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper implements ITransferMapper {

    @Override
    public Pcc01MWRequest mapperRequest(String personId, String accountId, Pcc01Request request) {
        return Pcc01MWRequest.builder()
                .personId(personId)
                .accountId(accountId)
                .amount(request.amount())
                .currency(request.currency())
                .build();
    }

    @Override
    public TransferResponse convert(TransferWalletMWResponse responseMW) {
        TransferWalletMWResponse.TransferMWData response = responseMW.getData();
        return TransferResponse.builder()
                .status(response.getStatus() != null ? response.getStatus() : "APPROVED")
                .transactionId(response.getIdReceipt())
                .maeId(response.getIdMaeTransaction())
                .accountingEntry(response.getAccountingEntry())
                .accountingDate(UtilDate.formatDate(response.getAccountingDate()))
                .accountingTime(response.getAccountingTime())
                .amountDebited(response.getAmountDebited())
                .amountCredited(response.getAmountCredited())
                .exchangeRateDebit(response.getExchangeRateDebit())
                .exchangeRateCredit(response.getExchangeRateCredit())
                .amount(response.getAmount())
                .currency(response.getCurrency())
                .fromAccountNumber(response.getFromAccountNumber())
                .fromHolder(response.getFromHolder())
                .toAccountNumber(response.getToAccountNumber())
                .toHolder(response.getToHolder())
                .description(response.getDescription())
                .fromCurrency(response.getFromCurrency())
                .toCurrency(response.getToCurrency())
                .build();
    }

    @Override
    public TransferResponse convert(TransferMWResponse responseMW) {
        TransferMWResponse.TransferMWData response = responseMW.getData();
        return TransferResponse.builder()
                .status(response.getStatus() != null ? response.getStatus() : "APPROVED")
                .transactionId(response.getIdReceipt())
                .maeId(response.getIdMaeTransaction())
                .accountingEntry(response.getAccountingEntry())
                .accountingDate(UtilDate.formatDate(response.getAccountingDate()))
                .accountingTime(response.getAccountingTime())
                .amountDebited(response.getAmountDebited())
                .amountCredited(response.getAmountCredited())
                .exchangeRateDebit(response.getExchangeRateDebit())
                .exchangeRateCredit(response.getExchangeRateCredit())
                .amount(response.getAmount())
                .currency(response.getCurrency())
                .fromAccountNumber(response.getFromAccountNumber())
                .fromHolder(response.getFromHolder())
                .toAccountNumber(response.getToAccountNumber())
                .toHolder(response.getToHolder())
                .description(response.getDescription())
                .fromCurrency(response.getFromCurrency())
                .toCurrency(response.getToCurrency())
                .build();
    }

    @Override
    public TransferResponse convert(TransferAchMwResponse responseMW) {
        TransferAchMwResponse.ResponseAch response = responseMW.getData();
        return TransferResponse.builder()
                .status(response.getStatus() != null ? response.getStatus() : "APPROVED")
                .transactionId(response.getIdReceipt())
                .maeId(response.getIdMaeTransaction())
                .accountingEntry(response.getTransferAchId())
                .accountingDate(UtilDate.formatDate(response.getAccountingDate()))
                .accountingTime(response.getAccountingTime())
                .amountDebited(response.getAmountDebited())
                .amountCredited(response.getAmountCredited())
                .exchangeRateDebit(response.getExchangeRateDebit())
                .exchangeRateCredit(response.getExchangeRateCredit())
                .amount(response.getAmount())
                .currency(response.getCurrency())
                .fromAccountNumber(response.getFromAccountNumber())
                .fromHolder(response.getFromHolder())
                .toAccountNumber(response.getToAccountNumber())
                .toHolder(response.getToHolder())
                .description(response.getDescription())
                .fromCurrency(response.getFromCurrency())
                .toCurrency(response.getToCurrency())
                .build();
    }
}
