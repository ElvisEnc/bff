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
public class LoyaltyInitialPointsResponse {
    @Schema(description = "Puntos VAMOS")
    @JsonProperty("points")
    private int points;

    @Schema(description = "fecha de puntos VAMOS")
    @JsonProperty("datePoints")
    private String datePoints;

    @Schema(description = "Puntos de lealtad")
    @JsonProperty("pointsLoyalty")
    private int pointsLoyalty;
}
