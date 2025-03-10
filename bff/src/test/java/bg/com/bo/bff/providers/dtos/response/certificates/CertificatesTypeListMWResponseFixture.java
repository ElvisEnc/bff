package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificatesTypeListMWResponse;

import java.util.Arrays;

public class CertificatesTypeListMWResponseFixture {

    public static CertificatesTypeListMWResponse withDefaults() {
        return CertificatesTypeListMWResponse.builder()
                .data(Arrays.asList(
                        CertificatesTypeListMWResponse.CertificateTypeMDW.builder()
                                .requestCode(123)
                                .typeCode(321)
                                .description("description")
                                .build(),
                        CertificatesTypeListMWResponse.CertificateTypeMDW.builder()
                                .requestCode(123)
                                .typeCode(321)
                                .description("description")
                                .build()
                ))
                .build();
    }
}
