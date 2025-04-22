package bg.com.bo.bff.application.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoyaltyMerchantVoucherCategoryResponse {

    @JsonProperty("redemptionVoucher")
    private Voucher[] redemptionVoucher;

    @JsonProperty("travelVoucher")
    private Voucher[] travelVoucher;

    @JsonProperty("productVoucher")
    private Voucher[] productVoucher;

    @JsonProperty("discountVoucher")
    private Voucher[] discountVoucher;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Voucher {
        @JsonProperty("identifier")
        private String identifier;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("redemptionValue")
        private String redemptionValue;

        @JsonProperty("typeVoucher")
        private String typeVoucher;

        @JsonProperty("banner")
        private String banner;

        @JsonProperty("merchantId")
        private String merchantId;

    }

}
