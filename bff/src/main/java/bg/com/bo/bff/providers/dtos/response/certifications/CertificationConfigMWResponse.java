package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationConfigMWResponse {

    private CertificationConfig data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CertificationConfig{
        private BigDecimal certPrice;
        private String message;
    }

}
