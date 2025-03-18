package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationsPreferredExchMWResponse {

    private List<PreferredExchangeMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PreferredExchangeMW {
        private String buyRateUFV;
        private String buyRateEUR;
        private String sellRateEur;
        private String buyRate;
        private String sellRate;
        private String client;
    }
}
