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
    private String merchantId;

    @JsonProperty("nombre")
    private String merchantName;

    @JsonProperty("descripcion")
    private String merchantDescription;

    @JsonProperty("logo")
    private String merchantLogo;

    @JsonProperty("isDestacado")
    private Byte isFeatured;

    @JsonProperty("isActivo")
    private Byte isActive;

    @JsonProperty("masBarato")
    private Integer merchantCheapest;

    @JsonProperty("categoria")
    private LoyaltyGetTradeCategoryResponse category;


}
