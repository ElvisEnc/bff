package bg.com.bo.bff.providers.dtos.request.debit.card;

public class UpdateDebitCardSecureMWRequestFixture {
    public static UpdateDebitCardSecureMWRequest withDefault() {
        return UpdateDebitCardSecureMWRequest.builder()
                .personId("123")
                .debitCardNew("123")
                .pciId("123")
                .acceptInsurance("123")
                .email("123")
                .requestNumberOld("123")
                .build();
    }
}