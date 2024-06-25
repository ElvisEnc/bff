package bg.com.bo.bff.providers.dtos.response.debit.card;

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
public class DCLockStatusMWResponse {
    @JsonProperty("data")
    private PCIData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class PCIData {
        @JsonProperty("idPci")
        private Integer pciId;
    }
}
