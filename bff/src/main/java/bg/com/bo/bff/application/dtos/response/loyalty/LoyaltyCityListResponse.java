package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyCityListResponse {
    private List<LoyaltyCity> data;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoyaltyCity {

        @Schema(description = "identificador")
        @JsonProperty("identifier")
        private String identifier;

        @Schema(description = "Nombre del comercio.")
        @JsonProperty("name")
        private String name;

    }
}
