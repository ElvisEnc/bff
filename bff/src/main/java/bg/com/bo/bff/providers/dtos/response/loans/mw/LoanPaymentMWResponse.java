package bg.com.bo.bff.providers.dtos.response.loans.mw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentMWResponse {
    private String status;
    private String idReceipt;
    private String idMaeTransaction;
    private String accountingEntry;
    private String accountingDate;
    private String accountingTime;
    private String originAccountNumber;
    private double amount;
    private String currency;
    private String fromHolder;
    private String fromCurrency;
    private double amountDebited;
    private double exchangeRateDebit;
    private double insuranceAmount;
    private String currencyInsurance;
    private double amountDebitInsurance;
    private double exchangeRateDebitInsurance;
    private String toLoanNumber;
    private double loanCapital;
    private double currentInterest;
    private double penaltyInterest;
    private double accruedCharges;
    private double formsAmount;
    private String nextDueDate;
    private double totalInstallments;
    private double paidInstallments;
    private double amountInsuranceCurrencyLoans;
}
