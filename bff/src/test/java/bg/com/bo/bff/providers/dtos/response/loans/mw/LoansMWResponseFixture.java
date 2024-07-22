package bg.com.bo.bff.providers.dtos.response.loans.mw;

import bg.com.bo.bff.application.dtos.response.payment.service.ValidateAffiliateCriteriaResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ApiErrorResponse;
import bg.com.bo.bff.providers.dtos.response.generic.ErrorDetailResponse;
import bg.com.bo.bff.providers.dtos.response.payment.service.mw.*;
import bg.com.bo.bff.providers.models.enums.middleware.payment.services.PaymentServicesMiddlewareError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                .state("State")
                .feePaymentDate("2021-09-01")
                .feePaymentDueDate("2021-09-01")
                .feeAmountK("100")
                .feeAmountI("100")
                .feeAmountC("100")
                .feePayment("100")
                .processDate("2021-09-01")
                .build();
    }
}