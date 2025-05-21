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
public class LoyaltyPromotionResponse {

    @Schema(description = "Identificador de la promocion")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Nombre de la promocion")
    @JsonProperty("namePromotion")
    private String namePromotion;

    @Schema(description = "Texto")
    @JsonProperty("text")
    private String text;

    @Schema(description = "Link")
    @JsonProperty("link")
    private String link;

    @Schema(description = "Ruta de la imagen")
    @JsonProperty("imagePath")
    private String imagePath;

    @Schema(description = "Informacion de la imagen")
    @JsonProperty("image")
    private LoyaltyImageResponse image;
}
