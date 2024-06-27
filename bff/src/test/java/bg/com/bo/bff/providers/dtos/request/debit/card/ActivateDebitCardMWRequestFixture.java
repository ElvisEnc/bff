package bg.com.bo.bff.providers.dtos.request.debit.card;

public class ActivateDebitCardMWRequestFixture {
    public static ActivateDebitCardMWRequest withDefault() {
        return ActivateDebitCardMWRequest.builder()
                .idPci("1234")
                .personId("1234")
                .build();
    }
}