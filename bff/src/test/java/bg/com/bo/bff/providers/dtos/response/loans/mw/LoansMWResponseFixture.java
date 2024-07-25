package bg.com.bo.bff.providers.dtos.response.loans.mw;

import java.util.Collections;

public class LoansMWResponseFixture {
    public static ListLoansMWResponse withDefaultListLoansMWResponse() {
        return ListLoansMWResponse.builder()
                .data(Collections.singletonList(withDefaultLoansItemMW()))
                .build();
    }

    private static ListLoansMWResponse.LoanDataMW withDefaultLoansItemMW() {
        return ListLoansMWResponse.LoanDataMW.builder()
                .loanId("12345")
                .loanNumber("12345")
                .customerName("John Doe")
                .disbursementDate("2021-08-01")
                .amountDisbursement("1000")
                .balance("500")
                .currency("USD")
                .currencyDescription("Dollar")
                .expirationDate("2022-08-01")
                .rate("0.1")
                .lastPaymentDate("2021-09-01")
                .product("Product")
                .stateCode("1")
                .state("Active")
                .feePaymentDate("2021-09-01")
                .feePaymentDueDate("2021-09-01")
                .feeAmountK("100")
                .feeAmountI("100")
                .feeAmountC("100")
                .feePayment("100")
                .processDate("2021-09-01")
                .build();
    }

    public static ListLoansMWResponse withDefaultListLoansMWResponseNull() {
        return ListLoansMWResponse.builder()
                .data(null)
                .build();
    }

    public static ListLoanPaymentsMWResponse withDefaultListLoanPaymentsMWResponse() {
        return ListLoanPaymentsMWResponse.builder()
                .data(Collections.singletonList(withDefaultLoanPaymentMW()))
                .build();
    }

    public static ListLoanPaymentsMWResponse.LoanPaymentMW withDefaultLoanPaymentMW() {
        return ListLoanPaymentsMWResponse.LoanPaymentMW.builder()
                .date("2024-07-11")
                .accountEntry("entry123")
                .advancedCapital("1000")
                .originalCapital("5000")
                .capitalPaid("500")
                .expenses("50")
                .interesAmountPaid("100")
                .payLateFees("10")
                .balance("4500")
                .typeMovement("payment")
                .totalInstallment("650")
                .branch("branch123")
                .build();
    }

    public static ListLoanPaymentsMWResponse withDefaultListLoanPaymentsMWResponseNull() {
        return ListLoanPaymentsMWResponse.builder()
                .data(null)
                .build();
    }
}