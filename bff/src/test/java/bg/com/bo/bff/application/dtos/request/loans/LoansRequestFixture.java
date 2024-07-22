package bg.com.bo.bff.application.dtos.request.loans;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;

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
                .field("loanId")
                .desc(false)
                .build();
    }
}
