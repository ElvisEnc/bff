package bg.com.bo.bff.providers.dtos.response.debit.card;

public class DebitCardMWResponseFixture {
    public static DCLimitsMWResponse withDefault() {
        return DCLimitsMWResponse.builder()
                .data(DCLimitsMWResponse.LimitsData.builder()
                        .pciId(1)
                        .build()
                )
                .build();
    }

    public static DCDetailMWResponse withDefaultDetail() {
        return DCDetailMWResponse.builder()
                .data(DCDetailMWResponse.CardData.builder()
                        .cardNumber("1234567890123456")
                        .typeCard("VISA")
                        .branch("1234")
                        .clientNumber("123456")
                        .personNumber("123456")
                        .cardName("John Doe")
                        .deliveryDate("2021-01-01")
                        .expirationDate("2021-12-31")
                        .status("A")
                        .statusDescription("Active")
                        .protectionInsurance("S")
                        .limitExpirationDate("2021-12-31")
                        .limitAmountME("1000")
                        .limitExtractions("5")
                        .build()
                )
                .build();
    }
}
