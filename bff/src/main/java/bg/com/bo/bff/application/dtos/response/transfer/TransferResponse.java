package bg.com.bo.bff.application.dtos.response.transfer;

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
public class TransferResponse {

        @JsonProperty("status")
        private String status;
        @JsonProperty("idTransaction")
        private String idTransaction;
        @JsonProperty("idMAE")
        private String idMAE;
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
