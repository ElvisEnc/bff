package bg.com.bo.bff.application.dtos.request.account.statement;

public class AccountStatementRequestFixture {
    public static ExtractRequest withDefaultExtractRequest() {
        return ExtractRequest.builder()
                .filters(ExtractFilter.builder()
                        .pagination(ExtractPagination.builder()
                                .startDate("2023-12-31")
                                .endDate("2024-04-01")
                                .page(1)
                                .pageSize(10)
                                .build())
                        .isAsc(false)
                        .type("")
                        .amount(AmountRange.builder()
                                .min(0.00)
                                .max(100.00)
                                .build())
                        .build())
                .build();
    }
}