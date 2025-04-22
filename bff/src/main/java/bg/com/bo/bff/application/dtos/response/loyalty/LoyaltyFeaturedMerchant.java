package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class LoyaltyFeaturedMerchant {

    @Schema(description = "identificador")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Nombre del comercio.")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descripcion")
    @JsonProperty("description")
    private String description;

    @Schema(description = "logotipo del comercio.")
    @JsonProperty("logo")
    private String logo;

    @Schema(description = "Costo mas barato.")
    @JsonProperty("cheapest")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer cheapest;

    @Schema(description = "Categoría del comercio.")
    @JsonProperty("category")
    private LoyaltyTradeCategoryResponse category;

}
