package bg.com.bo.bff.providers.dtos.response.login.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BiometricStatusMWResponse {
    private BiometricStatusData data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BiometricStatusData {
        private String statusBiometric;
        private String authenticationType;
    }
}
