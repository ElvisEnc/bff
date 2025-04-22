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
    @JsonProperty("merchantId")
    private String merchantId;

    @Schema(description = "Nombre del comercio.")
    @JsonProperty("merchantName")
    private String merchantName;

    @Schema(description = "Descripcion")
    @JsonProperty("merchantDescription")
    private String merchantDescription;

    @Schema(description = "logotipo del comercio.")
    @JsonProperty("merchantLogo")
    private String merchantLogo;

    @Schema(description = "Si es destacado")
    @JsonProperty("isFeatured")
    private Byte isFeatured;

    @Schema(description = "Si esta activado")
    @JsonProperty("isActive")
    private Byte isActive;

    @Schema(description = "Costo mas barato.")
    @JsonProperty("merchantCheapest")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer merchantCheapest;

    @Schema(description = "Categoría del comercio.")
    @JsonProperty("category")
    private LoyaltyTradeCategoryResponse category;

}
