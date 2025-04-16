package bg.com.bo.bff.application.dtos.request.remittance;

public class RemittanceRequestFixture {
    public static DepositRemittanceRequest withDefaultDepositRemittanceRequest() {
        return DepositRemittanceRequest.builder()
                .accountId("123456")
                .consultationId("123456789")
                .build();
    }

    public static ConsultWURemittanceRequest withDefaultConsultWURemittanceRequest(){
        return ConsultWURemittanceRequest.builder()
                .jtsOidAccount("363636")
                .build();
    }

    public static UpdateWURemittanceRequest withDefaultUpdateWURemittanceRequest(){
        return UpdateWURemittanceRequest.builder()
                .relation("Hermano")
                .origin("Remesas")
                .transaction("12322")
                .company("BANCO GANADERO")
                .companyLevel("7")
                .entryDate("2025-04-01")
                .laborType("Arquitecto")
                .build();
    }
}
