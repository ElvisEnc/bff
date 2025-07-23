package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDebtsDetail {
        @Schema(description = "Affiliation number")
        String affiliationNumber;

        @Schema(description = "service Payment Code")
        String servicePaymentCode;

        @Schema(description = "Company")
        String company;

        @Schema(description = "Accounting Date")
        String accountingDate;

        @Schema(description = "Accounting Time")
        String accountingTime;

        @Schema(description = "Accounting Entry")
        String accountingEntry;

        @Schema(description = "Currency")
        String currency;

        @Schema(description = "Amount")
        BigDecimal amount;

        @Schema(description = "Description")
        String description;

        @Schema(description = "From account number")
        String fromAccountNumber;

        @Schema(description = "From account holder")
        String fromHolder;

        @Schema(description = "Exchange amount")
        BigDecimal exchangeAmount;

        @Schema(description = "From account currency")
        String fromAccountCurrency;

        @Schema(description = "Exchange rate debit")
        String exchangeRateDebit;
}
