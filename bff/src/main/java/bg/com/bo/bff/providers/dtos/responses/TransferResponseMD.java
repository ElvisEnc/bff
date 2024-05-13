package bg.com.bo.bff.providers.dtos.responses;

import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.commons.utils.UtilDate;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseMD {
    @JsonProperty("data")
    private TransferMDData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransferMDData {
        @JsonProperty("idReceipt")
        private String idReceipt;
        @JsonProperty("accountingEntry")
        private String accountingEntry;
        @JsonProperty("accountingDate")
        private String accountingDate;
        @JsonProperty("accountingTime")
        private String accountingTime;
        @JsonProperty("amountDebited")
        private BigDecimal amountDebited;
        @JsonProperty("amountCredited")
        private BigDecimal amountCredited;
        @JsonProperty("exchangeRateDebit")
        private BigDecimal exchangeRateDebit;
        @JsonProperty("exchangeRateCredit")
        private BigDecimal exchangeRateCredit;
        @JsonProperty("amount")
        private BigDecimal amount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("fromAccountNumber")
        private String fromAccountNumber;
        @JsonProperty("fromHolder")
        private String fromHolder;
        @JsonProperty("toAccountNumber")
        private String toAccountNumber;
        @JsonProperty("toHolder")
        private String toHolder;
        @JsonProperty("description")
        private String description;
        @JsonProperty("fromCurrency")
        private String fromCurrency;
        @JsonProperty("toCurrency")
        private String toCurrency;
    }

    public static TransferResponseMD toFormat(TransferResponseMD response) {
        return TransferResponseMD.builder()
                .data(TransferMDData.builder()
                        .idReceipt(response.getData().getIdReceipt())
                        .accountingEntry(response.getData().getAccountingEntry())
                        .accountingDate(UtilDate.formatDateLong(response.getData().getAccountingDate()))
                        .accountingTime(UtilDate.formatTime(response.getData().getAccountingTime()))
                        .amountDebited(response.getData().getAmountDebited())
                        .amountCredited(response.getData().getAmountCredited())
                        .exchangeRateDebit(response.getData().getExchangeRateDebit())
                        .exchangeRateCredit(response.getData().getExchangeRateCredit())
                        .amount(response.getData().getAmount())
                        .currency(Util.convertCurrency(response.getData().getCurrency()))
                        .fromAccountNumber(response.getData().getFromAccountNumber())
                        .fromHolder(response.getData().getFromHolder())
                        .toAccountNumber(response.getData().getToAccountNumber())
                        .toHolder(response.getData().getToHolder())
                        .description(response.getData().getDescription())
                        .fromCurrency(response.getData().getFromCurrency())
                        .toCurrency(response.getData().getToCurrency())
                        .build())
                .build();

    }

}
