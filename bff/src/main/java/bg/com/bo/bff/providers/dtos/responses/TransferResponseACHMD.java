package bg.com.bo.bff.providers.dtos.responses;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseACHMD {
    @JsonProperty("data")
    private ResponseACH data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ResponseACH {
        @JsonProperty("status")
        private String status;

        @JsonProperty("idReceipt")
        private String idReceipt;

        @JsonProperty("transferAchId")
        private String transferAchId;

        @JsonProperty("receiptDetail")
        private ReceiptDetail receiptDetail;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class ReceiptDetail {
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
    }

    public static TransferResponseMD toFormat(TransferResponseACHMD response) {
        return TransferResponseMD.builder()
                .data(TransferResponseMD.TransferMDData.builder()
                        .status(response.getData().getStatus())
                        .idReceipt(response.getData().getIdReceipt())
                        .transferAchId(response.getData().getTransferAchId())
                        .accountingEntry(response.getData().getReceiptDetail().getAccountingEntry())
                        .accountingDate(response.getData().getReceiptDetail().getAccountingDate())
                        .accountingTime(response.getData().getReceiptDetail().getAccountingTime())
                        .amountDebited(response.getData().getReceiptDetail().getAmountDebited())
                        .amountCredited(response.getData().getReceiptDetail().getAmountCredited())
                        .exchangeRateDebit(response.getData().getReceiptDetail().getExchangeRateDebit())
                        .exchangeRateCredit(response.getData().getReceiptDetail().getExchangeRateCredit())
                        .amount(response.getData().getReceiptDetail().getAmount())
                        .currency(response.getData().getReceiptDetail().getCurrency())
                        .fromAccountNumber(response.getData().getReceiptDetail().getFromAccountNumber())
                        .fromHolder(response.getData().getReceiptDetail().getFromHolder())
                        .toAccountNumber(response.getData().getReceiptDetail().getToAccountNumber())
                        .toHolder(response.getData().getReceiptDetail().getToHolder())
                        .description(response.getData().getReceiptDetail().getDescription())
                        .fromCurrency(response.getData().getReceiptDetail().getFromCurrency())
                        .toCurrency(response.getData().getReceiptDetail().getToCurrency())
                        .build())
                .build();

    }
}


