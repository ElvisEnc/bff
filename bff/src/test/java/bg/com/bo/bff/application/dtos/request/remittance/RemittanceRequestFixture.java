package bg.com.bo.bff.application.dtos.request.remittance;

public class RemittanceRequestFixture {
    public static DepositRemittanceRequest withDefaultDepositRemittanceRequest() {
        return DepositRemittanceRequest.builder()
                .accountId("123456")
                .consultationId("123456789")
                .build();
    }

    public static DepositRemittanceWURequest withDefaultDepositRemittanceWURequest() {
        return DepositRemittanceWURequest.builder()
                .accountId("123456")
                .consultationId("123456789")
                .pccType("0")
                .originFunds("1")
                .originDestiny("1")
                .build();
    }
}
