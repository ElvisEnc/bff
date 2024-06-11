package bg.com.bo.bff.application.dtos.response.debit.card;

public class DebitCardFixture {
    public static DebitCard withDefault() {
        return DebitCard.builder()
                .id("123")
                .cardNumber("123")
                .holderName("123")
                .expiryDate("123")
                .status("123")
                .build();
    }
}