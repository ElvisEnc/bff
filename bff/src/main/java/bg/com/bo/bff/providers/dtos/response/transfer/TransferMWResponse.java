package bg.com.bo.bff.providers.dtos.response.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferMWResponse {
    @JsonProperty("data")
    private TransferMWData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransferMWData {
        @JsonProperty("status")
        private String status;
        @JsonProperty("idReceipt")
        private String idReceipt;
        @JsonProperty("idMaeTransaction")
        private String idMaeTransaction;
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

        @JsonSetter("status")
        public void setStatus(String status) {
            this.status = (status != null) ? status : "APPROVED";
        }

    }

    public static TransferMWResponse toFormat(TransferMWResponse response) {
        return TransferMWResponse.builder()
                .data(TransferMWData.builder()
                        .status(response.getData().getStatus() != null ? response.getData().getStatus() : "APPROVED")
                        .idReceipt(response.getData().getIdReceipt())
                        .accountingEntry(response.getData().getAccountingEntry())
                        .accountingDate(response.getData().getAccountingDate())
                        .accountingTime(response.getData().getAccountingTime())
                        .amountDebited(response.getData().getAmountDebited())
                        .amountCredited(response.getData().getAmountCredited())
                        .exchangeRateDebit(response.getData().getExchangeRateDebit())
                        .exchangeRateCredit(response.getData().getExchangeRateCredit())
                        .amount(response.getData().getAmount())
                        .currency(response.getData().getCurrency())
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
