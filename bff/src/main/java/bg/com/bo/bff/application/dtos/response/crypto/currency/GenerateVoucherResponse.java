package bg.com.bo.bff.application.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
    private BigDecimal currency;

    @JsonProperty("currencyEquivalentCredit")
    private BigDecimal currencyCredit;

    @JsonProperty("currencyEquivalentDebit")
    private BigDecimal currencyDebit;

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
    private BigDecimal creditExchangeRate;

    @JsonProperty("description")
    private String description;
}
