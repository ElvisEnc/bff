package bg.com.bo.bff.application.dtos.response.debit.card;

import java.util.Arrays;

public class ListAccountTDResponseFixture {
    public static ListAccountTDResponse withDefault() {
        return ListAccountTDResponse.builder()
                .data(Arrays.asList(AccountTDFixture.withDefault(), AccountTDFixture.withDefault()))
                .build();
    }
}