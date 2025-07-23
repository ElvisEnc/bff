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
public class LoyaltyGetCategoryPromotionResponse {
    @JsonProperty("id")
    private String identifier;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("texto")
    private String text;

    @JsonProperty("link")
    private String link;

    @JsonProperty("rutaImagenMiniatura")
    private String routeImageThumbnail;
}
