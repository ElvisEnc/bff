package bg.com.bo.bff.providers.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSetter;
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
        @JsonProperty("status")
        private String status;
        @JsonProperty("idReceipt")
        private String idReceipt;
        @JsonProperty("accountingEntry")
        private String accountingEntry;
        @JsonProperty("transferAchId")
        private String transferAchId;
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

        @JsonSetter("transferAchId")
        public void setTransferAchId(String transferAchId) {
            this.transferAchId = (transferAchId != null) ? transferAchId : "0";
        }
    }

    public static TransferResponseMD toFormat(TransferResponseMD response) {
        return TransferResponseMD.builder()
                .data(TransferMDData.builder()
                        .status(response.getData().getStatus() != null ? response.getData().getStatus() : "APPROVED")
                        .idReceipt(response.getData().getIdReceipt())
                        .transferAchId(response.getData().getTransferAchId() != null ? response.getData().getTransferAchId() : "0")
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
