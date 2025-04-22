package bg.com.bo.bff.providers.dtos.response.loyalty;

import bg.com.bo.bff.application.dtos.response.loyalty.LoyaltyTradeCategoryResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoyaltyGetStoreFeaturedResponse {

    @JsonProperty("id")
    private String identifier;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("isDestacado")
    private Integer isFeatured;

    @JsonProperty("idCategoria")
    private String categoryId;

    @JsonProperty("categoria")
    private LoyaltyGetTradeCategoryResponse category;

    @JsonProperty("isActivo")
    private Integer isActive;

    @JsonProperty("masBarato")
    private Integer cheaper;

}
