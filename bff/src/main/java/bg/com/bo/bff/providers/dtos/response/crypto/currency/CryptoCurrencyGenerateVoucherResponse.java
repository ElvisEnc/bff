package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGenerateVoucherResponse {
    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @JsonProperty("data")
    private VoucherResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VoucherResponse {

        @JsonProperty("transactionNumber")
        private Integer transactionNumber;

        @JsonProperty("transactionType")
        private Integer transactionType;

        @JsonProperty("amount")
        private Double amount;

        @JsonProperty("currencyAmount")
        private Integer currencyAmount;

        @JsonProperty("currencyEquivalentCredit")
        private Integer currencyEquivalentCredit;

        @JsonProperty("currencyEquivalentDebit")
        private Integer currencyEquivalentDebit;

        @JsonProperty("senderName")
        private String senderName;

        @JsonProperty("senderAccountNumber")
        private String senderAccountNumber;

        @JsonProperty("senderNdJts")
        private Integer senderNdJts;

        @JsonProperty("receiverName")
        private String receiverName;

        @JsonProperty("receiverNdJts")
        private Integer receiverNdJts;

        @JsonProperty("receiverAccountNumber")
        private String receiverAccountNumber;

        @JsonProperty("receiverBank")
        private String receiverBank;

        @JsonProperty("equivalentDebitAmount")
        private Double equivalentDebitAmount;

        @JsonProperty("debitExchangeRate")
        private Double debitExchangeRate;

        @JsonProperty("equivalentCreditAmount")
        private Double equivalentCreditAmount;

        @JsonProperty("creditExchangeRate")
        private Double creditExchangeRate;

        @JsonProperty("description")
        private String description;
    }
}
