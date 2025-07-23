package bg.com.bo.bff.application.dtos.request.export;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.qr.OrderRequest;
import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;

import java.math.BigDecimal;

public class AccountStatementExportRequestFixture {
    public static AccountStatementExportRequest withDefaultAccountStatementExportRequest() {
        return AccountStatementExportRequest.builder()
                .format("PDF")
                .filters(withDefaultExportFilters())
                .refreshData(false)
                .build();
    }

    public static AccountStatementExportRequest.ExportFilters withDefaultExportFilters() {
        return AccountStatementExportRequest.ExportFilters.builder()
                .date(withDefaultPeriodRequest())
                .order(withDefaultOrderRequest())
                .amount(withDefaultAmountRange())
                .movementType("1")
                .build();
    }

    public static PeriodRequest withDefaultPeriodRequest() {
        return PeriodRequest.builder()
                .start("2024-01-01")
                .end("2024-12-31")
                .build();
    }

    public static OrderRequest withDefaultOrderRequest() {
        return OrderRequest.builder()
                .field("DATE")
                .desc(true)
                .build();
    }

    public static AmountRange withDefaultAmountRange() {
        return AmountRange.builder()
                .min(new BigDecimal("100.00"))
                .max(new BigDecimal("1000.00"))
                .build();
    }

    public static AccountStatementExportRequest withDefaultAccountStatementExportRequestCsv() {
        return AccountStatementExportRequest.builder()
                .format("CSV")
                .filters(withDefaultExportFiltersAmount())
                .refreshData(false)
                .build();
    }

    public static AccountStatementExportRequest.ExportFilters withDefaultExportFiltersAmount() {
        return AccountStatementExportRequest.ExportFilters.builder()
                .date(withDefaultPeriodRequest())
                .order(withDefaultOrderRequestAmount())
                .amount(withDefaultAmountRange())
                .movementType("1")
                .build();
    }

    public static OrderRequest withDefaultOrderRequestAmount() {
        return OrderRequest.builder()
                .field("AMOUNT")
                .desc(false)
                .build();
    }

    public static AccountStatementExportRequest withDefaultAccountStatementExportRequestOrderNull() {
        return AccountStatementExportRequest.builder()
                .format("CSV")
                .filters(withDefaultExportFiltersAmountOrderNull())
                .refreshData(false)
                .build();
    }

    public static AccountStatementExportRequest.ExportFilters withDefaultExportFiltersAmountOrderNull() {
        return AccountStatementExportRequest.ExportFilters.builder()
                .date(withDefaultPeriodRequest())
                .order(null)
                .amount(null)
                .movementType("1")
                .build();
    }
}