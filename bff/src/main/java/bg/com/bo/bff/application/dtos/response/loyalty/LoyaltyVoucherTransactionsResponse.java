package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyVoucherTransactionsResponse extends LoyaltyGenericVoucherTransactionResponse {

    @Schema(description = "Indicador si el vale fue canjeado (0: no, 1: sí)")
    @JsonProperty("redeemed")
    private Integer redeemed;

    @Schema(description = "Identificador del administrador o gestor que realizó el canje")
    @JsonProperty("managerId")
    private String managerId;

    @Schema(description = "Costo monetario del vale")
    @JsonProperty("voucherCost")
    private Integer voucherCost;

    @Schema(description = "Porcentaje asumido por la empresa o institución")
    @JsonProperty("assumedPercentage")
    private Integer assumedPercentage;
}
