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
}
