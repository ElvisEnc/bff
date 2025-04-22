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
public class LoyaltyTradeCategoryResponse {

    @Schema(description = "identificador")
    @JsonProperty("categoryId")
    private String categoryId;

    @Schema(description = "Nombre de la categoria.")
    @JsonProperty("nameCategory")
    private String nameCategory;

    @Schema(description = "Icono")
    @JsonProperty("iconCategory")
    private String iconCategory;
}
