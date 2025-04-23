package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGetLevelResponse {

    @JsonProperty("id")
    private String identifier;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("puntajeMinimo")
    private int minimumScore;

    @JsonProperty("puntajeMaximo")
    private int maximumScore;

    @JsonProperty("idCampana")
    private String idCampaign;

    @JsonProperty("idNivelSiguiente")
    private String idLevelNext;
}
