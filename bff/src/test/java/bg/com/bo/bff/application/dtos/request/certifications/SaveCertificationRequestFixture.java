package bg.com.bo.bff.application.dtos.request.certifications;

public class SaveCertificationRequestFixture {

    public static SaveCertificationRequest withDefaults() {
        return SaveCertificationRequest.builder()
                .personId("1234")
                .accountId("412351324")
                .chargeFeeId("222")
                .typeCode("159")
                .requestCode("951")
                .nit("123546")
                .clientName("Nombre Cliente")
                .initDate("1/1/2025")
                .endDate("1/1/2025")
                .build();
    }

}
