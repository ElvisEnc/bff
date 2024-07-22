package bg.com.bo.bff.application.dtos.response.loans;

import bg.com.bo.bff.providers.dtos.response.generic.ApiDataResponse;

import java.util.Collections;
import java.util.List;

public class LoansResponseFixture {
    private static ListLoansResponse withDefaultItemListLoansResponse() {
        return ListLoansResponse.builder()
                .loanId("1")
                .customerName("John Doe")
                .disbursementDate("2021-08-01")
                .amountDisbursement(1000.0)
                .balance(1000.0)
                .currency("USD")
                .expirationDate("2022-08-01")
                .rate(0.1)
                .product("Personal Loan")
                .stateCode("1")
                .state("Active")
                .feePaymentDate("2021-09-01")
                .feePaymentDueDate("2021-09-01")
                .feeAmountK(100.0)
                .feeAmountI(10.0)
                .feeAmountC(10.0)
                .accountLoan("123456789")
                .feePayment(120.0)
                .processDate("2021-08-01")
                .build();

    }

    public static List<ListLoansResponse> withDataDefaultListLoansResponse() {
        return Collections.singletonList(withDefaultItemListLoansResponse());
    }
}
