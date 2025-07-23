package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationConfigMWResponse;

import java.math.BigDecimal;

public class CertificationConfigMWResponseFixture {

    public static CertificationConfigMWResponse withDefaults(){
        return CertificationConfigMWResponse.builder()
                .data(
                        CertificationConfigMWResponse.CertificationConfig.builder()
                                .certPrice(new BigDecimal(123))
                                .message("message")
                                .build()
                )
                .build();
    }

}
