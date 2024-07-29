package bg.com.bo.bff.application.dtos.response.loans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoansResponseFixture {
    private static ListLoansResponse withDefaultItemListLoansResponse() {
        return ListLoansResponse.builder()
                .loanId("12345")
                .loanNumber("12345")
                .customerName("John Doe")
                .disbursementDate("2021-08-01")
                .amountDisbursement(1000.0)
                .balance(500.0)
                .currency("USD")
                .expirationDate("2022-08-01")
                .rate(0.1)
                .product("Product")
                .stateCode("1")
                .state("Active")
                .feePaymentDate("2021-09-01")
                .feePaymentDueDate("2021-09-01")
                .feeAmountK(100.0)
                .feeAmountI(100.0)
                .feeAmountC(100.0)
                .feePayment(100.0)
                .processDate("2021-09-01")
                .build();

    }

    public static List<ListLoansResponse> withDataDefaultListLoansResponse() {
        return Collections.singletonList(withDefaultItemListLoansResponse());
    }

    public static LoanPaymentsResponse withDefaultLoanPaymentsResponse() {
        return LoanPaymentsResponse.builder()
                .date("2024-07-11")
                .accountEntry("entry123")
                .advancedCapital("1000")
                .originalCapital("5000")
                .capitalPaid("500")
                .expenses("50")
                .interestAmountPaid("100")
                .payLateFees("10")
                .balance("4500")
                .typeMovement("payment")
                .totalInstallment("650")
                .branch("branch123")
                .build();
    }

    public static List<LoanPaymentsResponse> withDataDefaultLoanPaymentsResponse() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanPaymentsResponse()));
    }

    public static LoanInsurancePaymentsResponse withDefaultLoanInsurancePaymentsResponse() {
        return LoanInsurancePaymentsResponse.builder()
                .warrantyNumber(123456L)
                .description("Seguro de Prestamo")
                .currencyCode("USD")
                .currencyDescription("Dolar Americano")
                .paymentNumber(1)
                .paymentDate("2024-07-11")
                .amount(new BigDecimal("100.00"))
                .index(0)
                .build();
    }

    public static List<LoanInsurancePaymentsResponse> withDataDefaultLoanInsurancePaymentsResponse() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanInsurancePaymentsResponse()));
    }
}
