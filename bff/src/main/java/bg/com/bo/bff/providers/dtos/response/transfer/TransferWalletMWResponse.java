package bg.com.bo.bff.providers.dtos.response.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferWalletMWResponse {
    @JsonProperty("data")
    private TransferMWData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransferMWData {
        @JsonProperty("status")
        private String status;
        @JsonProperty("nroTransaction")
        private String nroTransaction;
        @JsonProperty("identifierMae")
        private String identifierMae;
        @JsonProperty("receiptDetail")
        private ReceiptDetail receiptDetail;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ReceiptDetail {
            private String accountingEntry;
            private String accountingDate;
            private String accountingTime;
            private double amountDebited;
            private double amountCredited;
            private double exchangeRateDebit;
            private double exchangeRateCredit;
            private double amount;
            private String currency;
            private String fromAccountNumber;
            private String fromHolder;
            private String toAccountNumber;
            private String toHolder;
            private String description;
            private String fromCurrency;
            private String toCurrency;
        }
    }
}
