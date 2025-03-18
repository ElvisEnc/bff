package bg.com.bo.bff.providers.dtos.response.certificates;

import bg.com.bo.bff.providers.dtos.response.certifications.CertificationsPreferredExchMWResponse;

import java.util.Arrays;

public class CertificationsPreferredExchMWResponseFixture {

    public static CertificationsPreferredExchMWResponse withDefaults() {
        return CertificationsPreferredExchMWResponse.builder()
                .data(
                        Arrays.asList(
                                CertificationsPreferredExchMWResponse.PreferredExchangeMW.builder()
                                        .buyRateUFV("2.58274")
                                        .buyRateEUR("6.91238")
                                        .sellRateEur("8.55139")
                                        .buyRate("6.85000")
                                        .sellRate("6.97000")
                                        .client("0")
                                        .build(),
                                CertificationsPreferredExchMWResponse.PreferredExchangeMW.builder()
                                        .buyRateUFV("2.58274")
                                        .buyRateEUR("6.91238")
                                        .sellRateEur("8.55139")
                                        .buyRate("6.85000")
                                        .sellRate("6.97000")
                                        .client("0")
                                        .build()
                        )
                )
                .build();
    }

    public static CertificationsPreferredExchMWResponse withEmptyData() {
        return CertificationsPreferredExchMWResponse.builder()
                .data(null)
                .build();
    }

}
