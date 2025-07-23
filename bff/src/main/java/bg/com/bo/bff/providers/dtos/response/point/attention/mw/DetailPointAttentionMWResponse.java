package bg.com.bo.bff.providers.dtos.response.point.attention.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPointAttentionMWResponse {
    private List<PoinAttention> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PoinAttention {
        @JsonProperty("IdPoint")
        private int idPoint;
        @JsonProperty("IdReference")
        private String idReference;
        @JsonProperty("Direction")
        private String direction;
        @JsonProperty("Phone")
        private String phone;
        @JsonProperty("Valor")
        private String valor;
        @JsonProperty("IdClave")
        private String idClave;
        @JsonProperty("IdProperty")
        private int idProperty;
    }
}
