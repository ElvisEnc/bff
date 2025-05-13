package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGenerateVoucherResponse {
    @Schema(description = "errorCode")
    private String codeError;

    @Schema(description = "errorMessage")
    private String message;

    @Schema(description = "data")
    private VoucherResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VoucherResponse {

        @Schema(description = "transactionNumber")
        private Integer transactionNumber;

        @Schema(description = "transactionType")
        private int transactionType;

        @Schema(description = "currencyAmount")
        private Integer currencyAmount;

        @Schema(description = "currencyEquivalentCredit")
        private Integer currencyEquivalentCredit;

        @Schema(description = "currencyEquivalentDebit")
        private Integer currencyEquivalentDebit;

        @Schema(description = "senderName")
        private String senderName;

        @Schema(description = "senderAccountNumber")
        private String senderAccountNumber;

        @Schema(description = "senderNdJts")
        private Long senderNdJts;

        @Schema(description = "receiverName")
        private String receiverName;

        @Schema(description = "receiverNdJts")
        private Long receiverNdJts;

        @Schema(description = "receiverAccountNumber")
        private String receiverAccountNumber;

        @Schema(description = "receiverBank")
        private String receiverBank;

        @Schema(description = "equivalentDebitAmount")
        private BigDecimal equivalentDebitAmount;

        @Schema(description = "debitExchangeRate")
        private Double debitExchangeRate;

        @Schema(description = "equivalentCreditAmount")
        private BigDecimal equivalentCreditAmount;

        @Schema(description = "creditExchangeRate")
        private Double creditExchangeRate;

        @Schema(description = "description")
        private String description;
    }
}
