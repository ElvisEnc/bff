package bg.com.bo.bff.providers.dtos.response.login.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoutMWResponse {
    private LogoutData data;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogoutData {
        private String id;
    }
}
