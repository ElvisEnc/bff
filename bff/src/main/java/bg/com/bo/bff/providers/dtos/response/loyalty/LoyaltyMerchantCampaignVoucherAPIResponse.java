package bg.com.bo.bff.providers.dtos.response.loyalty;

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
public class LoyaltyMerchantCampaignVoucherAPIResponse {

    @JsonProperty("beneficiosConsumos")
    private Voucher[] redemptionVoucher;

    @JsonProperty("beneficiosPasajes")
    private Voucher[] travelVoucher;

    @JsonProperty("beneficiosProductos")
    private Voucher[] productVoucher;

    @JsonProperty("beneficiosDescuentos")
    private Voucher[] discountVoucher;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Voucher {
        @JsonProperty("id")
        private String identifier;

        @JsonProperty("nombre")
        private String name;

        @JsonProperty("descripcion")
        private String description;

        @JsonProperty("valorCanje")
        private String redemptionValue;

        @JsonProperty("tipoBeneficio")
        private String typeVoucher;

        @JsonProperty("banner")
        private String banner;

        @JsonProperty("idComercio")
        private String merchantId;

    }

}
