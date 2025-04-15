package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LoyaltyGeneralInformationResponse {
    @JsonProperty("codigoSistema")
    private int codeSystem;

    @JsonProperty("nivel")
    private LoyaltyGetLevelResponse level;

    @JsonProperty("niveles")
    private List<LoyaltyGetLevelResponse> levels;

    @JsonProperty("puntos")
    private BigDecimal points;

    @JsonProperty("puntosPeriodo")
    private BigDecimal pointsPeriod;

}
