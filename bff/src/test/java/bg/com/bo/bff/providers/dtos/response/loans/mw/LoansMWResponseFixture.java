package bg.com.bo.bff.providers.dtos.response.loans.mw;

import java.math.BigDecimal;
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

    public static LoanPaymentsMWResponse withDefaultListLoanPaymentsMWResponse() {
        return LoanPaymentsMWResponse.builder()
                .data(Collections.singletonList(withDefaultLoanPaymentMW()))
                .build();
    }

    public static LoanPaymentsMWResponse.LoanPaymentMW withDefaultLoanPaymentMW() {
        return LoanPaymentsMWResponse.LoanPaymentMW.builder()
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

    public static LoanPaymentsMWResponse withDefaultListLoanPaymentsMWResponseNull() {
        return LoanPaymentsMWResponse.builder()
                .data(null)
                .build();
    }

    public static LoanInsurancePaymentsMWResponse withDefaultLoanInsurancePaymentsMWResponse() {
        return LoanInsurancePaymentsMWResponse.builder()
                .data(Collections.singletonList(withDefaultLoanInsurancePaymentMW()))
                .build();
    }

    public static LoanInsurancePaymentsMWResponse.LoanInsurancePaymentMW withDefaultLoanInsurancePaymentMW() {
        return LoanInsurancePaymentsMWResponse.LoanInsurancePaymentMW.builder()
                .paymentNumber(1)
                .paymentDate("2024-07-11")
                .warrantyNumber(123456L)
                .description("Seguro de Prestamo")
                .currency("USD")
                .currencyDescription("Dolar Americano")
                .amount(new BigDecimal("100.00"))
                .record(0)
                .build();
    }

    public static LoanInsurancePaymentsMWResponse withDefaultLoanInsurancePaymentsMWResponseNull() {
        return LoanInsurancePaymentsMWResponse.builder()
                .data(null)
                .build();
    }
}