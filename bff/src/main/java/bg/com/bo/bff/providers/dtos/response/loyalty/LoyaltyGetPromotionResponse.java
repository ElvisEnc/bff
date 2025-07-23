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
public class LoyaltyGetPromotionResponse {
    @JsonProperty("id")
    private String identifier;

    @JsonProperty("nombre")
    private String namePromotion;

    @JsonProperty("texto")
    private String text;

    @JsonProperty("link")
    private String link;

    @JsonProperty("rutaImagen")
    private String imagePath;

    @JsonProperty("imagen")
    private LoyaltyGetImageResponse image;

}
