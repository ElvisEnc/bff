package bg.com.bo.bff.providers.dtos.response.nps.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNpsMWResponse {

    private RegisterNpsMWResponse.RegisterNpsMW data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterNpsMW {

        private Integer identifierNps;
        private String codeResponse;
        private String messageResponse;
        private List<NpsMW> cursorNpsOut;

    }
}

