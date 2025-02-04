package bg.com.bo.bff.application.dtos.request.remittance;

public class RemittanceRequestFixture {
    public static DepositRemittanceRequest withDefaultDepositRemittanceRequest() {
        return DepositRemittanceRequest.builder()
                .accountId("123456")
                .consultationId("123456789")
                .build();
    }
}
