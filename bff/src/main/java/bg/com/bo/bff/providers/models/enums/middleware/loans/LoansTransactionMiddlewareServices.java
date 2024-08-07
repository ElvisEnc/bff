package bg.com.bo.bff.providers.models.enums.middleware.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoansTransactionMiddlewareServices {
    PAY_LOAN_INSTALLMENT("/bs/v1/loans/payment");

    private final String serviceURL;
}

