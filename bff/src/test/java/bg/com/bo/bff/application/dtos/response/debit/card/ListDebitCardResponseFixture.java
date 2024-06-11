package bg.com.bo.bff.application.dtos.response.debit.card;

import java.util.Arrays;

public class ListDebitCardResponseFixture {
    public static ListDebitCardResponse withDefault() {
        return ListDebitCardResponse.builder()
                .data(Arrays.asList(DebitCardFixture.withDefault(), DebitCardFixture.withDefault()))
                .build();
    }
}