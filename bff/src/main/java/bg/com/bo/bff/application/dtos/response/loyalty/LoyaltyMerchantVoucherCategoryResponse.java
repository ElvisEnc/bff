package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoyaltyMerchantVoucherCategoryResponse {

    @JsonProperty("redemptionVoucher")
    private LoyaltyVoucherResponse[] redemptionVoucher;

    @JsonProperty("travelVoucher")
    private LoyaltyVoucherResponse[] travelVoucher;

    @JsonProperty("productVoucher")
    private LoyaltyVoucherResponse[] productVoucher;

    @JsonProperty("discountVoucher")
    private LoyaltyVoucherResponse[] discountVoucher;

}
