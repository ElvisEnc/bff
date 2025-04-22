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
public class LoyaltyVoucherConsumptionResponse {
    @Schema(description = "Valor del vale de consumo")
    @JsonProperty("valueVoucher")
    private String valueVoucher;

    @Schema(description = "Tipo de valor (por ejemplo: Bs, %)")
    @JsonProperty("valueType")
    private String valueType;
}
