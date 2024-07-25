package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.qr.PeriodRequest;

public class LoansRequestFixture {

    public static ListLoansRequest withDefaultListLoansRequest() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoanFilters.builder()
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
                .field("field")
                .desc(false)
                .build();
    }

    public static ListLoansRequest withDefaultListLoansRequestNull() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoanFilters.builder()
                        .pagination(null)
                        .order(null)
                        .build())
                .refreshData(true)
                .build();
    }

    public static ListLoansRequest withDefaultListLoansRequestLoanNumber() {
        return ListLoansRequest.builder()
                .filters(ListLoansRequest.LoanFilters.builder()
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
    public static LoanPaymentsRequest.FilterLoanPayment withDefaultFilterLoanPayment() {
        return LoanPaymentsRequest.FilterLoanPayment.builder()
                .pagination(withDefaultPaginationRequest())
                .order(withDefaultOrderRequest())
                .paymentDate(withDefaultPeriodRequest())
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
    public static LoanPaymentsRequest.FilterLoanPayment withDefaultFilterLoanPaymentNull() {
        return LoanPaymentsRequest.FilterLoanPayment.builder()
                .pagination(null)
                .order(null)
                .paymentDate(null)
                .build();
    }

    public static LoanPaymentsRequest withDefaultLoanPaymentsRequestOrderFilter() {
        return LoanPaymentsRequest.builder()
                .loanNumber("123")
                .filters(withDefaultFilterLoanPaymentOrderInterestPaid())
                .refreshData(true)
                .build();
    }
    public static LoanPaymentsRequest.FilterLoanPayment withDefaultFilterLoanPaymentOrderInterestPaid() {
        return LoanPaymentsRequest.FilterLoanPayment.builder()
                .pagination(null)
                .order(withDefaultOrderRequestInterestPaid())
                .paymentDate(withDefaultPeriodRequestFilter())
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
    public static LoanPaymentsRequest.FilterLoanPayment withDefaultFilterLoanPaymentOrderCapitalPaid() {
        return LoanPaymentsRequest.FilterLoanPayment.builder()
                .pagination(null)
                .order(withDefaultOrderRequestCapitalPaid())
                .paymentDate(withDefaultPeriodRequestFilterCapitalPaid())
                .build();
    }

    private static OrderRequest withDefaultOrderRequestCapitalPaid() {
        return OrderRequest.builder()
                .field("CAPITAL_PAID")
                .desc(true)
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
    public static LoanPaymentsRequest.FilterLoanPayment withDefaultFilterLoanPaymentOrderDate() {
        return LoanPaymentsRequest.FilterLoanPayment.builder()
                .pagination(null)
                .order(withDefaultOrderRequestDate())
                .paymentDate(withDefaultPeriodRequestFilterDate())
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
}
