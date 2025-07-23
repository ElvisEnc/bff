package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DCLimitsMWResponse {
    @JsonProperty("data")
    private LimitsData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class LimitsData {
        @JsonProperty("pciId")
        private Integer pciId;
    }
}
