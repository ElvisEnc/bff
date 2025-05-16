package bg.com.bo.bff.application.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Double amount;

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
    private Integer senderJts;

    @JsonProperty("receiverName")
    private String receiverName;

    @JsonProperty("receiverNdJts")
    private Integer receiverJts;

    @JsonProperty("receiverAccountNumber")
    private String receiverAccountNumber;

    @JsonProperty("receiverBank")
    private String receiverBank;

    @JsonProperty("equivalentDebitAmount")
    private Double debitAmount;

    @JsonProperty("debitExchangeRate")
    private Double debitExchangeRate;

    @JsonProperty("equivalentCreditAmount")
    private Double creditAmount;

    @JsonProperty("creditExchangeRate")
    private Double creditExchangeRate;

    @JsonProperty("description")
    private String description;
}
