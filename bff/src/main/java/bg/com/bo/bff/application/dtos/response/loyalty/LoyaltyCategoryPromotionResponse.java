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
public class LoyaltyCategoryPromotionResponse {
    @Schema(description = "Identificador de la categoria promocion")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Nombre de la categoria promocion")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Texto de la categoria promocion")
    @JsonProperty("text")
    private String text;

    @Schema(description = "Link de la categoria promocion")
    @JsonProperty("link")
    private String link;

    @Schema(description = "Ruta de la imagen en miniatura")
    @JsonProperty("routeImageThumbnail")
    private String routeImageThumbnail;
}
