package bg.com.bo.bff.application.dtos.request.certifications;

public class CertificationConfigRequestFixture {

    public static CertificationConfigRequest withDefaults() {
        return CertificationConfigRequest.builder()
                .personId("1234")
                .requestCode("155")
                .requestTypeCode("951")
                .build();
    }

}
