package bg.com.bo.bff.providers.dtos.request.certifications;

public class CertificationPriceMWRequestFixture {

    public static CertificationPriceMWRequest withDefaults() {
        return CertificationPriceMWRequest.builder()
                .personId("126302")
                .appCode("2")
                .session("string")
                .initDate("16/12/2023")
                .endDate("16/12/2023")
                .certCode("797")
                .certTypeCode("118")
                .build();
    }

}
