package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLimitMWResponse {
    private UpdateLimitMW data;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateLimitMW {
        private String identifier;
    }
}
