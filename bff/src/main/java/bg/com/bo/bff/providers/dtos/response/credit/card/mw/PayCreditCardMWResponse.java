package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCreditCardMWResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("maeId")
    private String maeId;
    @JsonProperty("transactionNum")
    private String transactionNum;
    @JsonProperty("accountingEntry")
    private String accountingEntry;
    @JsonProperty("accountingDate")
    private String accountingDate;
    @JsonProperty("accountingTime")
    private String accountingTime;
    @JsonProperty("amountDebited")
    private BigDecimal amountDebited;

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

    @JsonProperty("description")
    private String description;
    @JsonProperty("fromCurrency")
    private String fromCurrency;
    @JsonProperty("toCurrency")
    private String toCurrency;
    @JsonProperty("tcAccountId")
    private String tcAccountId;
}
