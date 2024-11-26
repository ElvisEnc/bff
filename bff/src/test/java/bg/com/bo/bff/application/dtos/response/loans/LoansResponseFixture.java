package bg.com.bo.bff.application.dtos.response.loans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoansResponseFixture {
    private static ListLoansResponse withDefaultItemListLoansResponse() {
        return ListLoansResponse.builder()
                .loanId("12345")
                .loanNumber("12345")
                .clientId("12345")
                .customerName("John Doe")
                .disbursementDate("2021-08-01")
                .amountDisbursement(1000.0)
                .balance(500.0)
                .currencyCode("068")
                .expirationDate("2022-08-01")
                .rate(0.1)
                .product("Product")
                .lastPaymentDate("2021-08-01")
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
//                .date("2024-07-11")
                .date("11/07/2024")
                .accountEntry("entry123")
                .advancedCapital(BigDecimal.valueOf(1000).setScale(2, RoundingMode.DOWN))
                .originalCapital(BigDecimal.valueOf(5000).setScale(2, RoundingMode.DOWN))
                .capitalPaid(BigDecimal.valueOf(500).setScale(2, RoundingMode.DOWN))
                .expenses(BigDecimal.valueOf(50).setScale(2, RoundingMode.DOWN))
                .interestAmountPaid(BigDecimal.valueOf(100).setScale(2, RoundingMode.DOWN))
                .payLateFees(BigDecimal.valueOf(10).setScale(2, RoundingMode.DOWN))
                .balance(BigDecimal.valueOf(4500).setScale(2, RoundingMode.DOWN))
                .typeMovement("payment")
                .totalInstallment(BigDecimal.valueOf(650).setScale(2, RoundingMode.DOWN))
                .branch("branch123")
                .build();
    }

    public static List<LoanPaymentsResponse> withDataDefaultLoanPaymentsResponse() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanPaymentsResponse()));
    }

    public static List<LoanPaymentsResponse> withDataDefaultLoanPaymentsResponseBigDecimalNull() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanPaymentsResponseBigDecimalNull()));
    }

    public static LoanPaymentsResponse withDefaultLoanPaymentsResponseBigDecimalNull() {
        return LoanPaymentsResponse.builder()
                .date("11/07/2024")
                .accountEntry("entry123")
                .advancedCapital(null)
                .originalCapital(null)
                .capitalPaid(null)
                .expenses(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.DOWN))
                .interestAmountPaid(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.DOWN))
                .payLateFees(BigDecimal.valueOf(10.00).setScale(2, RoundingMode.DOWN))
                .balance(BigDecimal.valueOf(4500.00).setScale(2, RoundingMode.DOWN))
                .typeMovement("payment")
                .totalInstallment(BigDecimal.valueOf(650.00).setScale(2, RoundingMode.DOWN))
                .branch("branch123")
                .build();
    }

    public static LoanInsurancePaymentsResponse withDefaultLoanInsurancePaymentsResponse() {
        return LoanInsurancePaymentsResponse.builder()
                .warrantyNumber(123456L)
                .description("Seguro de Prestamo")
                .currencyCode("USD")
                .currencyDescription("Dolar Americano")
                .paymentNumber(1)
                .paymentDate("11/07/2024")
                .amount(new BigDecimal("100.00"))
                .index(0)
                .build();
    }

    public static List<LoanInsurancePaymentsResponse> withDataDefaultLoanInsurancePaymentsResponse() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanInsurancePaymentsResponse()));
    }

    public static LoanPlanResponse withDefaultLoanPlanResponse() {
        return LoanPlanResponse.builder()
                .identifier(1L)
                .loanId(101L)
                .loanNumber(202L)
                .quotaNumber(1L)
                .dateInit("01/01/2024")
                .dateDue("31/12/2024")
                .quotaType("Mensual")
                .capital(new BigDecimal("1000.00"))
                .interest(new BigDecimal("50.00"))
                .charge1(new BigDecimal("10.00"))
                .charge2(new BigDecimal("5.00"))
                .charge3(new BigDecimal("3.00"))
                .charge4(new BigDecimal("2.00"))
                .charge5(new BigDecimal("1.00"))
                .charge6(new BigDecimal("0.50"))
                .residual(new BigDecimal("500.00"))
                .dateRegister("01/01/2024")
                .clientCode(123456L)
                .clientName("Juan Perez")
                .product("Personal Loan")
                .branchName("Main Branch")
                .currency("USD")
                .disbursementDate("01/01/2024")
                .nominalRate(new BigDecimal("5.00"))
                .period(12L)
                .teac(new BigDecimal("5.50"))
                .disbursementAmount(new BigDecimal("10000.00"))
                .timeLimit(12L)
                .rateType("Fixed")
                .nameTypeRate("Fixed Rate")
                .dateReviewRate("01/01/2024")
                .baseRateReviewPoint(new BigDecimal("0.50"))
                .baseRate(new BigDecimal("5.00"))
                .paymentTypeInterest("Monthly")
                .quantityDues(12L)
                .build();
    }

    public static List<LoanPlanResponse> withDataDefaultLoanPlanResponse() {
        return new ArrayList<>(Collections.singletonList(withDefaultLoanPlanResponse()));
    }

    public static LoanDetailPaymentResponse withDefaultLoanDetailPaymentResponse() {
        return LoanDetailPaymentResponse.builder()
                .correlativeId(123456L)
                .nroOperation(123456L)
                .highDate("2024-07-11")
                .totalFee(10)
                .feePaid(5)
                .expirationNextDate("2024-07-11")
                .expirationLoanDate("2024-07-11")
                .interestRate(0.1)
                .dateValue("2024-07-11")
                .currentBalance(100.0)
                .status("Active")
                .balanceSecure(100.0)
                .accruedCharges(100.0)
                .interestCurrent(100.0)
                .capital(100.0)
                .form(1)
                .amount(100.0)
                .secureCurrency(1)
                .amountSecureMandatory(100.0)
                .build();
    }

    public static LoanPaymentResponse withDataDefaultLoanPaymentResponse() {
        return LoanPaymentResponse.builder()
                .status("Success")
                .transactionId("123")
                .maeId("123")
                .accountingEntry("entry123")
                .accountingDate("2024-07-11")
                .accountingTime("12:00:00")
                .originAccountNumber("123456")
                .amount(1000.0)
                .currencyCode("068")
                .fromHolder("John Doe")
                .fromCurrencyCode("068")
                .amountDebited(1000.0)
                .exchangeRateDebit(1.0)
                .insuranceAmount(100.0)
                .currencyCodeInsurance("068")
                .amountDebitInsurance(100.0)
                .exchangeRateDebitInsurance(1.0)
                .loanId("123")
                .loanCapital(1000.0)
                .currentInterest(100.0)
                .penaltyInterest(100.0)
                .accruedCharges(100.0)
                .formsAmount(100.0)
                .nextDueDate("2024-08-11")
                .totalInstallments(12)
                .paidInstallments(1)
                .build();
    }
}
