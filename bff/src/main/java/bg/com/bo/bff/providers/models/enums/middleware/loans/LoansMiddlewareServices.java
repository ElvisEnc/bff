package bg.com.bo.bff.providers.models.enums.middleware.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoansMiddlewareServices {
    GET_LIST_LOANS("/bs/v1/loans/persons/%s"),
    GET_LIST_LOAN_PAYMENTS("/bs/v1/loans/%s/loan-number/%s/payments");
    private final String serviceURL;
}

