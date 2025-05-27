package bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisableDeviceMWResponse {
    private DataMWResponse data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataMWResponse {
        private String code;
        private String message;

    }


}
