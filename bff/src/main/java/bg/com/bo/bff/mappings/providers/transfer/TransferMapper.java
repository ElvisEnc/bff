package bg.com.bo.bff.mappings.providers.transfer;

import bg.com.bo.bff.application.dtos.response.transfer.TransferResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferAchMwResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferMWResponse;
import bg.com.bo.bff.providers.dtos.response.transfer.TransferWalletMWResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferMapper implements ITransferMapper {

    @Override
    public TransferResponse convert(TransferWalletMWResponse responseMW) {
        TransferWalletMWResponse.TransferMWData response = responseMW.getData();
        return TransferResponse.builder()
                .status(response.getStatus() != null ? response.getStatus() : "APPROVED")
                .transactionId(response.getNroTransaction())
                .maeId(response.getIdentifierMae())
                .accountingEntry(response.getReceiptDetail().getAccountingEntry())
                .accountingDate(Util.formatDate(response.getReceiptDetail().getAccountingDate()))
                .accountingTime(response.getReceiptDetail().getAccountingTime())
                .amountDebited(BigDecimal.valueOf(response.getReceiptDetail().getAmountDebited()))
                .amountCredited(BigDecimal.valueOf(response.getReceiptDetail().getAmountCredited()))
                .exchangeRateDebit(BigDecimal.valueOf(response.getReceiptDetail().getExchangeRateDebit()))
                .exchangeRateCredit(BigDecimal.valueOf(response.getReceiptDetail().getExchangeRateCredit()))
                .amount(BigDecimal.valueOf(response.getReceiptDetail().getAmount()))
                .currency(response.getReceiptDetail().getCurrency())
                .fromAccountNumber(response.getReceiptDetail().getFromAccountNumber())
                .fromHolder(response.getReceiptDetail().getFromHolder())
                .toAccountNumber(response.getReceiptDetail().getToAccountNumber())
                .toHolder(response.getReceiptDetail().getToHolder())
                .description(response.getReceiptDetail().getDescription())
                .fromCurrency(response.getReceiptDetail().getFromCurrency())
                .toCurrency(response.getReceiptDetail().getToCurrency())
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
                .accountingDate(Util.formatDate(response.getAccountingDate()))
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
                .transactionId(response.getTransferAchId())
                .maeId(response.getMaeIdTransaction())
                .accountingEntry(response.getReceiptDetail().getAccountingEntry())
                .accountingDate(Util.formatDate(response.getReceiptDetail().getAccountingDate()))
                .accountingTime(response.getReceiptDetail().getAccountingTime())
                .amountDebited(response.getReceiptDetail().getAmountDebited())
                .amountCredited(response.getReceiptDetail().getAmountCredited())
                .exchangeRateDebit(response.getReceiptDetail().getExchangeRateDebit())
                .exchangeRateCredit(response.getReceiptDetail().getExchangeRateCredit())
                .amount(response.getReceiptDetail().getAmount())
                .currency(response.getReceiptDetail().getCurrency())
                .fromAccountNumber(response.getReceiptDetail().getFromAccountNumber())
                .fromHolder(response.getReceiptDetail().getFromHolder())
                .toAccountNumber(response.getReceiptDetail().getToAccountNumber())
                .toHolder(response.getReceiptDetail().getToHolder())
                .description(response.getReceiptDetail().getDescription())
                .fromCurrency(response.getReceiptDetail().getFromCurrency())
                .toCurrency(response.getReceiptDetail().getToCurrency())
                .build();
    }
}
