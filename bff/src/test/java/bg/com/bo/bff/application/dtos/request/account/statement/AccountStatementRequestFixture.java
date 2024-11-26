package bg.com.bo.bff.application.dtos.request.account.statement;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;

import java.math.BigDecimal;

public class AccountStatementRequestFixture {
    public static AccountStatementsRequest getDefaultAccountStatementsRequest() {
        return AccountStatementsRequest.builder()
                .filters(getDefaultAccountStatementsFilter())
                .refreshData(false)
                .build();
    }

    public static AccountStatementsRequest.AccountStatementsFilter getDefaultAccountStatementsFilter() {
        return AccountStatementsRequest.AccountStatementsFilter.builder()
                .date(getDefaultPeriodRequest())
                .pagination(getDefaultPaginationRequest())
                .order(getDefaultOrderRequest())
                .amount(getDefaultAmountRange())
                .movementType("1")
                .build();
    }

    public static PeriodRequest getDefaultPeriodRequest() {
        return PeriodRequest.builder()
                .start("2023-11-22")
                .end("2024-04-30")
                .build();
    }

    public static PaginationRequest getDefaultPaginationRequest() {
        return PaginationRequest.builder()
                .page(1)
                .pageSize(10)
                .build();
    }

    public static OrderRequest getDefaultOrderRequest() {
        return OrderRequest.builder()
                .field("DATE")
                .desc(true)
                .build();
    }

    public static AmountRange getDefaultAmountRange() {
        return AmountRange.builder()
                .min(new BigDecimal("0.50"))
                .max(new BigDecimal("1000.00"))
                .build();
    }
}