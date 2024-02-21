package bg.com.bo.bff.controllers.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonRootName(value = "OwnAccountTransferRequest")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "Transfer Own Account", description = "Transfer Own Account Response")
public class TransferResponse {

    @Schema(description = "idReceipt")
    private String idReceipt;


    @JsonProperty
    @Schema(description = "Accounting Entry")
    private String accountingEntry;

    @JsonProperty
    @Schema(description = "Accounting Date")
    private LocalDate accountingDate;

    @JsonProperty
    @Schema(description = "accountingTime")
    private String accountingTime;

    @JsonProperty
    @Schema(description = "Status")
    private String status;

    @JsonProperty
    @Schema(description = "amountDebited")
    private BigDecimal amountDebited;

    @JsonProperty
    @Schema(description = "amountCredited")
    private BigDecimal amountCredited;

    @JsonProperty
    @Schema(description = "exchangeRateDebit")
    private BigDecimal exchangeRateDebit;

    @JsonProperty
    @Schema(description = "exchangeRateCredit")
    private BigDecimal exchangeRateCredit;

    @JsonProperty
    @Schema(description = "Amount")
    private BigDecimal amount;

    @JsonProperty
    @Schema(description = "Currency")
    private String currency;

    @JsonProperty
    @Schema(description = "From account number")
    private String fromAccountNumber;

    @JsonProperty
    @Schema(description = "From account holder")
    private String fromHolder;

    @JsonProperty
    @Schema(description = "To account number")
    private String toAccountNumber;

    @JsonProperty
    @Schema(description = "To account holder")
    private String toHolder;

    @JsonProperty
    @Schema(description = "Description")
    private String description;

    @JsonProperty
    @Schema(description = "toBankName")
    private String toBankName;

    @JsonProperty
    @Schema(description = "From currency")
    private String fromCurrency;

    @JsonProperty
    @Schema(description = "to currency")
    private String toCurrency;


}
