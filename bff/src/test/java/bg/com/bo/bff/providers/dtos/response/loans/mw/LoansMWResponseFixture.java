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

    public static LoanPlanMWResponse withDefaultLoanPlanMWResponse() {
        return LoanPlanMWResponse.builder()
                .data(Collections.singletonList(withDefaultLoanPlanMW()))
                .build();
    }

    public static LoanPlanMWResponse.LoanPlanMW withDefaultLoanPlanMW() {
        return LoanPlanMWResponse.LoanPlanMW.builder()
                .identifier(1L)
                .loanId(101L)
                .loanNumber(202L)
                .quotaNumber(1L)
                .dateInit("2024-01-01")
                .dateDue("2024-12-31")
                .quotaType("Mensual")
                .capital(new BigDecimal("1000.00"))
                .interest(new BigDecimal("50.00"))
                .charge1(new BigDecimal("10.00"))
                .charge2(new BigDecimal("5.00"))
                .charge3(new BigDecimal("3.00"))
                .charge4(new BigDecimal("2.00"))
                .charge5(new BigDecimal("1.00"))
                .charge6(new BigDecimal("0.50"))
                .remanent(new BigDecimal("500.00"))
                .dateRegister("2024-01-01")
                .clientCode(123456L)
                .clientName("Juan Perez")
                .product("Personal Loan")
                .branchName("Main Branch")
                .currency("USD")
                .disbursementDate("2024-01-01")
                .nominalRate(new BigDecimal("5.00"))
                .period(12L)
                .teac(new BigDecimal("5.50"))
                .disbursementAmount(new BigDecimal("10000.00"))
                .timeLimit(12L)
                .rateType("Fixed")
                .nameTypeRate("Fixed Rate")
                .dateReviewRate("2024-01-01")
                .baseRateReviewPoint(new BigDecimal("0.50"))
                .baseRate(new BigDecimal("5.00"))
                .paymentTypeInterest("Monthly")
                .quantityDue(12L)
                .build();
    }

    public static LoanPlanMWResponse withDefaultLoanPlanMWResponseNull() {
        return LoanPlanMWResponse.builder()
                .data(null)
                .build();
    }
}