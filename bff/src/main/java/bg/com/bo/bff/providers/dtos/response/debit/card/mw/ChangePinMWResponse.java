package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePinMWResponse {
    @JsonProperty("data")
    private DetailData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class DetailData {
        @JsonProperty("idPci")
        private Integer idPci;
    }
}
