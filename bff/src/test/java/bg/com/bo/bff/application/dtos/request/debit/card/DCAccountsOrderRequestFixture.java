package bg.com.bo.bff.application.dtos.request.debit.card;

import java.util.List;

public class DCAccountsOrderRequestFixture {
    public static DCAccountsOrderRequest withDefault() {
        return DCAccountsOrderRequest.builder()
                .data(List.of(DCAccountsOrderRequest.Account.builder()
                        .accountId("1234567890")
                        .build()))
                .build();
    }
}
