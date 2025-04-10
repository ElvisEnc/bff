package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyLevel {

    @Schema(description = "identificador")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "nombre")
    @JsonProperty("name")
    private String name;

    @Schema(description = "minimo puntaje")
    @JsonProperty("minimumScore")
    private int minimumScore;

    @Schema(description = "maximo puntaje")
    @JsonProperty("maximumScore")
    private int maximumScore;

    @Schema(description = "identificador de la campaña")
    @JsonProperty("idCampaign")
    private String idCampaign;

    @Schema(description = "identificador del siguiente nivel")
    @JsonProperty("idLevelNext")
    private String idLevelNext;


}
