package bg.com.bo.bff.application.dtos.response.debit.card;

public class AccountTDFixture {
    public static AccountTD withDefault() {
        return AccountTD.builder()
                .accountId("123")
                .accountNumber("123")
                .description("123")
                .build();
    }
}