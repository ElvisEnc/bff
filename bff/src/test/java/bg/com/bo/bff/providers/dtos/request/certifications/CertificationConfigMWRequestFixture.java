package bg.com.bo.bff.providers.dtos.request.certifications;

public class CertificationConfigMWRequestFixture {

    public static CertificationConfigMWRequest withDefaults() {
        return CertificationConfigMWRequest.builder()
                .personId("1234")
                .languageCode("1")
                .requestCode("777")
                .requestTypeCode("123")
                .build();
    }

}
