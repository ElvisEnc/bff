package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationSaveRequestMWResponse;

public class CertificationSaveRequestMWResponseFixture {

    public static CertificationSaveRequestMWResponse withDefaults() {
        return CertificationSaveRequestMWResponse.builder()
                .data(
                        CertificationSaveRequestMWResponse.SaveResponse.builder()
                                .requestNumber("12345")
                                .responseCode("COD000")
                                .build()
                )
                .build();
    }

    public static CertificationSaveRequestMWResponse withError() {
        return CertificationSaveRequestMWResponse.builder()
                .data(
                        CertificationSaveRequestMWResponse.SaveResponse.builder()
                                .requestNumber("0")
                                .responseCode("COD001")
                                .build()
                )

                .build();
    }

}
