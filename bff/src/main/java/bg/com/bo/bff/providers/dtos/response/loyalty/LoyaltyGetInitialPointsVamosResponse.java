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
public class LoyaltyGetInitialPointsVamosResponse {

    @JsonProperty("puntosVamos")
    private int pointsVamos;

    @JsonProperty("fechaPuntosVamos")
    private String datePointsVamos;

    @JsonProperty("puntosLealtad")
    private int pointsLoyalty;
}
