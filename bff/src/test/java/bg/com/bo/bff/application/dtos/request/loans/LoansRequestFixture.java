package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;

public class LoansRequestFixture {

    public static ListLoansRequest withDefaultListLoansRequest() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoansFilter.builder()
                        .pagination(withDefaultPaginationRequest())
                        .order(withDefaultOrderRequest())
                        .build())
                .refreshData(true)
                .build();
    }

    private static PaginationRequest withDefaultPaginationRequest() {
        return PaginationRequest.builder()
                .page(1)
                .pageSize(10)
                .build();
    }

    private static OrderRequest withDefaultOrderRequest() {
        return OrderRequest.builder()
                .field("LOAN_NUMBER")
                .desc(false)
                .build();
    }

    public static ListLoansRequest withDefaultListLoansRequestNull() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoansFilter.builder()
                        .pagination(null)
                        .order(null)
                        .build())
                .refreshData(true)
                .build();
    }

    public static ListLoansRequest withDefaultListLoansRequestLoanNumber() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoansFilter.builder()
                        .pagination(withDefaultPaginationRequestOne())
                        .order(withDefaultOrderRequestLoanNumber())
                        .build())
                .refreshData(true)
                .build();
    }

    private static PaginationRequest withDefaultPaginationRequestOne() {
        return PaginationRequest.builder()
                .page(1)
                .pageSize(1)
                .build();
    }

    private static OrderRequest withDefaultOrderRequestLoanNumber() {
        return OrderRequest.builder()
                .field("LOAN_NUMBER")
                .desc(true)
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequest() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPayment())
                .refreshData(false)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPayment() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(withDefaultPaginationRequest())
                .order(withDefaultOrderRequestInterestPaid())
                .date(withDefaultPeriodRequest())
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequest() {
        return PeriodRequest.builder()
                .start("2024-07-11")
                .end("2024-07-11")
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestNull() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentNull())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentNull() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(null)
                .date(null)
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestOrderFilter() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentOrderInterestPaid())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentOrderInterestPaid() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(withDefaultOrderRequestInterestPaid())
                .date(withDefaultPeriodRequestFilter())
                .build();
    }

    private static OrderRequest withDefaultOrderRequestInterestPaid() {
        return OrderRequest.builder()
                .field("INTEREST_PAID")
                .desc(true)
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequestFilter() {
        return PeriodRequest.builder()
                .start("2024-07-20")
                .end("2024-07-20")
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestOrderFilterCapitalPaid() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentOrderCapitalPaid())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentOrderCapitalPaid() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(withDefaultOrderRequestCapitalPaid())
                .date(withDefaultPeriodRequestFilterCapitalPaid())
                .build();
    }

    private static OrderRequest withDefaultOrderRequestCapitalPaid() {
        return OrderRequest.builder()
                .field("CAPITAL_PAID")
                .desc(false)
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequestFilterCapitalPaid() {
        return PeriodRequest.builder()
                .start("2024-07-01")
                .end("2024-07-01")
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestOrderFilterDate() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentOrderDate())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentOrderDate() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(withDefaultOrderRequestDate())
                .date(withDefaultPeriodRequestFilterDate())
                .build();
    }

    private static OrderRequest withDefaultOrderRequestDate() {
        return OrderRequest.builder()
                .field("DATE")
                .desc(true)
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequestFilterDate() {
        return PeriodRequest.builder()
                .start("2024-07-02")
                .end("2024-07-30")
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanInsurancePaymentsRequest() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanInsurancePayment())
                .refreshData(false)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanInsurancePayment() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(withDefaultPaginationRequest())
                .order(withDefaultOrderRequestDate())
                .date(withDefaultPeriodRequest())
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanInsurancePaymentsRequestOrderFilter() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentOrderAmountPaid())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentOrderAmountPaid() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(withDefaultOrderRequestAmountPaidFalse())
                .date(null)
                .build();
    }

    private static OrderRequest withDefaultOrderRequestAmountPaidFalse() {
        return OrderRequest.builder()
                .field("AMOUNT_PAID")
                .desc(false)
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestDateInvalid() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentDateInvalid())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.LoanPaymentsFilter withDefaultFilterLoanPaymentDateInvalid() {
        return LoanPaymentsRequest.LoanPaymentsFilter.builder()
                .pagination(null)
                .order(null)
                .date(withDefaultPeriodRequestDateInvalid())
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequestDateInvalid() {
        return PeriodRequest.builder()
                .start("2024-07-01")
                .end("2024-06-01")
                .build();
    }
}
