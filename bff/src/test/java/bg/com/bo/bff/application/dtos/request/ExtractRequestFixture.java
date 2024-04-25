package bg.com.bo.bff.application.dtos.request;


import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractFilter;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractPagination;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;

public class ExtractRequestFixture {
    public static ExtractRequest withDefault() {
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