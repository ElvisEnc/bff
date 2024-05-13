package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.application.dtos.request.destination.account.PaginationRequest;

public class QrListRequestFixture {
    public static QrListRequest withDefault() {
        return QrListRequest.builder()
                .filters(QrListFilterRequest.builder()
                        .period(PeriodRequest.builder()
                                .start("2023-11-22")
                                .end("2024-04-30")
                                .build())
                        .build())
                .pagination(PaginationRequest.builder()
                        .page(1)
                        .pageSize(10)
                        .build())
                .order(QrListOrderRequest.builder()
                        .field("REGISTRATION_DATE")
                        .desc(true)
                        .build())
                .build();
    }
}