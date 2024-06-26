package bg.com.bo.bff.providers.dtos.response.debit.card;

public class UpdateSecureMWResponseFixture {
    public static UpdateSecureMWResponse withDefault() {
        return UpdateSecureMWResponse.builder()
                .data(withDataDefault())
                .build();
    }

    public static UpdateSecureMWResponse.UpdateSecureMW withDataDefault() {
        return UpdateSecureMWResponse.UpdateSecureMW.builder()
                .idPci(123)
                .build();
    }
}