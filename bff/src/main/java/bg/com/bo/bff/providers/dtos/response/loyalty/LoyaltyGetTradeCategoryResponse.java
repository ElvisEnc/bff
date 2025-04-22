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
public class LoyaltyGetTradeCategoryResponse {

    @JsonProperty("id")
    private String categoryId;

    @JsonProperty("nombre")
    private String nameCategory;

    @JsonProperty("icono")
    private String iconCategory;

}
