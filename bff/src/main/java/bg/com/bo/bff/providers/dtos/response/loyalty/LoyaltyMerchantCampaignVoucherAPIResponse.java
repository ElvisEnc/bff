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
public class LoyaltyMerchantCampaignVoucherAPIResponse {

    @JsonProperty("beneficiosConsumos")
    private LoyaltyGetVoucherResponse[] redemptionVoucher;

    @JsonProperty("beneficiosPasajes")
    private LoyaltyGetVoucherResponse[] travelVoucher;

    @JsonProperty("beneficiosProductos")
    private LoyaltyGetVoucherResponse[] productVoucher;

    @JsonProperty("beneficiosDescuentos")
    private LoyaltyGetVoucherResponse[] discountVoucher;


}
