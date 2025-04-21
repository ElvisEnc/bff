package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LoyaltyGetTransactionsResponse extends LoyaltyGetGenericTransactionsResponse {

    @JsonProperty("isCanjeado")
    private Integer redeemed;

    @JsonProperty("administradorGestorId")
    private String managerId;

    @JsonProperty("costoVale")
    private Integer voucherCost;

    @JsonProperty("porcentajeAsumido")
    private Integer assumedPercentage;

}
