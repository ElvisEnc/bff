package bg.com.bo.bff.application.dtos.request.debit.card;

public class UpdateDebitCardAssuranceRequestFixture {
    public static UpdateDebitCardAssuranceRequest withDefault() {
        return UpdateDebitCardAssuranceRequest.builder()
                .email("123@dominio.io")
                .openingRequestFlow(false)
                .openingRequestNumber("123")
                .build();
    }
}