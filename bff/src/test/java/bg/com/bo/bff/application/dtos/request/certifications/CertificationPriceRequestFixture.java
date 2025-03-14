package bg.com.bo.bff.application.dtos.request.certifications;

public class CertificationPriceRequestFixture {

    public static CertificationPriceRequest withDefaults() {
        return CertificationPriceRequest.builder()
                .personId("1234")
                .initDate("2025-01-01")
                .endDate("2025-01-01")
                .certCode("123")
                .certTypeCode("321")
                .build();
    }

}
