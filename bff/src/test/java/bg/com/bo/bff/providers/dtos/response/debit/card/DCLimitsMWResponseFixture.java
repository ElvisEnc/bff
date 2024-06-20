package bg.com.bo.bff.providers.dtos.response.debit.card;

public class DCLimitsMWResponseFixture {
    public static DCLimitsMWResponse withDefault() {
        return DCLimitsMWResponse.builder()
                .data(withDefaultLimisData())
                .build();
    }

    public static DCLimitsMWResponse.LimitsData withDefaultLimisData() {
        return DCLimitsMWResponse.LimitsData.builder()
                .pciId(123)
                .build();
    }
}