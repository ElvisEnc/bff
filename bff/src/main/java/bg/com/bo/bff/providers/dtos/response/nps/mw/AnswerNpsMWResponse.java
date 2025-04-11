package bg.com.bo.bff.providers.dtos.response.nps.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerNpsMWResponse {

    private AnswerNpsMWResponse.AnswerNpsMW data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AnswerNpsMW {
        @JsonProperty("codeResponse")
        private String codeResponse;
        @JsonProperty("messageResponse")
        private String messageResponse;
    }
}
