package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationPriceMWResponse;

public class CertificationPriceMWResponseFixture {

    public static CertificationPriceMWResponse withDefaults(){
        return CertificationPriceMWResponse.builder()
                .roleId(1)
                .eventId(1)
                .amount(123)
                .currencyDes("Bs")
                .currencyCode(0)
                .rangeFrom(1)
                .rangeTo(2)
                .rangeType("string")
                .build();
    }

}
