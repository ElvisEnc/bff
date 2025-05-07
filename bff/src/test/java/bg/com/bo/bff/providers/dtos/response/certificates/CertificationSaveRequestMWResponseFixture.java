package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationSaveRequestMWResponse;

public class CertificationSaveRequestMWResponseFixture {

    public static CertificationSaveRequestMWResponse withDefaults() {
        return CertificationSaveRequestMWResponse.builder()
                .data(
                        CertificationSaveRequestMWResponse.SaveResponse.builder()
                                .certPrice("50.0")
                                .requestDate("2025-01-01")
                                .requestTime("10:10")
                                .fromCurrency("068")
                                .originAccount("123456")
                                .clientAccountName("NOMBRE")
                                .email("test@bg.com.bo")
                                .certDescription("DESCRIPTION")
                                .dateRange("FROM 1 TO 2 MONTHS")
                                .requestNumber("321654987")
                                .build()
                )
                .build();
    }

}
