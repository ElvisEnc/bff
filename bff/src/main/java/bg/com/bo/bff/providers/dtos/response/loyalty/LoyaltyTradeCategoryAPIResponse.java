package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoyaltyTradeCategoryAPIResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("nombre")
    private String name;

    @JsonProperty("icono")
    private String icon;

}
