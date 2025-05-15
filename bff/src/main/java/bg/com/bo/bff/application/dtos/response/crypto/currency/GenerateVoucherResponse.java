package bg.com.bo.bff.application.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateVoucherResponse {

    @JsonProperty("transactionNumber")
    private Integer transactionNumber;

    @JsonProperty("transactionType")
    private Integer transactionType;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currencyAmount")
    private Integer currency;

    @JsonProperty("currencyEquivalentCredit")
    private Integer currencyCredit;

    @JsonProperty("currencyEquivalentDebit")
    private Integer currencyDebit;

    @JsonProperty("senderName")
    private String senderName;

    @JsonProperty("senderAccountNumber")
    private String senderAccountNumber;

    @JsonProperty("senderNdJts")
    private Long senderJts;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("receiverNdJts")
    private Long receiverJts;

    @JsonProperty("receiverAccountNumber")
    private String receiverAccountNumber;

    @JsonProperty("receiverBank")
    private String receiverBank;

    @JsonProperty("equivalentDebitAmount")
    private BigDecimal debitAmount;

    @JsonProperty("debitExchangeRate")
    private Double debitExchangeRate;

    @JsonProperty("equivalentCreditAmount")
    private BigDecimal creditAmount;

    @JsonProperty("creditExchangeRate")
    private Double creditExchangeRate;

    @JsonProperty("description")
    private String description;
}
