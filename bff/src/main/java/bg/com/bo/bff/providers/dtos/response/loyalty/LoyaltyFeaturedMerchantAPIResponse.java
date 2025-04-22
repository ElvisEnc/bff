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
public class LoyaltyFeaturedMerchantAPIResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("descripcion")
    private String description;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("isDestacado")
    private Byte isFeatured;

    @JsonProperty("isActivo")
    private Byte isActive;

    @JsonProperty("masBarato")
    private Integer cheapest;

    @JsonProperty("categoria")
    private LoyaltyTradeCategoryAPIResponse category;


}
