package bg.com.bo.bff.providers.dtos.request.nps.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerNpsMWRequest {
    private int nroPerson;
    private int codNps;
    private String identifierDevice;
    private List<ResponseMWRequest> response;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseMWRequest {
        private int id;
        private String valor;
    }
}
