package bg.com.bo.bff.providers.dtos.response.loans.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailPaymentMWResponse {
    private String identifier;
    private String loanNumber;
    private String highDate;
    private String totalFee;
    private String feePaid;
    private String expirationNextDate;
    private String expirationLoanDate;
    private String interestRate;
    private String dateValue;
    private String currentBalance;
    private String status;
    private String balanceSecure;
    private String accruedCharges;
    private String interestCurrent;
    private String capital;
    private String penaltyInterest;
    private String form;
    private String amount;
    private String secureCurrency;
    private String amountSecureMandatory;
    private String amountSecureConvertMandatory;
    private String errorCode;
}
