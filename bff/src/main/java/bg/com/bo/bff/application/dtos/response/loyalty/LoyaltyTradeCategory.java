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
public class LoyaltyTradeCategory {

    @Schema(description = "identificador")
    @JsonProperty("identifier")
    private String identifier;

    @Schema(description = "Nombre de la categoria.")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Icono")
    @JsonProperty("icon")
    private String icon;


}
