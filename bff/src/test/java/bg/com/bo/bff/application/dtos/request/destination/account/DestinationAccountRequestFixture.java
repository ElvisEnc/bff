package bg.com.bo.bff.application.dtos.request.destination.account;

import static org.junit.jupiter.api.Assertions.*;

public class DestinationAccountRequestFixture {
    public static DestinationAccountRequest withDefault() {
        return DestinationAccountRequest.builder()
                .name("")
                .pagination(
                        PaginationRequest.builder()
                                .page(1)
                                .pageSize(10)
                                .build()
                )
                .build();
    }
}