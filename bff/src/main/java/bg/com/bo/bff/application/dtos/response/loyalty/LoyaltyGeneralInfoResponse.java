package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGeneralInfoResponse {
    @Schema(description = "codigo del sistema")
    @JsonProperty("codeSystem")
    private int codeSystem;

    @Schema(description = "nivel")
    @JsonProperty("level")
    private LoyaltyLevelResponse level;

    @Schema(description = "lista de niveles")
    @JsonProperty("levels")
    private List<LoyaltyLevelResponse> levels;

    @Schema(description = "Puntaje")
    @JsonProperty("points")
    private BigDecimal points;

    @Schema(description = "Puntos del periodo")
    @JsonProperty("pointsPeriod")
    private BigDecimal pointsPeriod;

}
