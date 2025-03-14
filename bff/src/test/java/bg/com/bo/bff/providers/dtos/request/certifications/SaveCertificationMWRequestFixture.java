package bg.com.bo.bff.providers.dtos.request.certifications;

public class SaveCertificationMWRequestFixture {

    public static SaveCertificationMWRequest withDefaults() {
        return SaveCertificationMWRequest.builder()
                .personId("string")
                .accountId("string")
                .chargeFeeId("string")
                .typeCode("string")
                .requestCode("string")
                .nit("string")
                .clientName("string")
                .endDate("22/11/9359")
                .initDate("30/10/3661")
                .build();
    }

}
