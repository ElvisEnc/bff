package bg.com.bo.bff.providers.dtos.response.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyGetVoucherResponse {

    @JsonProperty("id")
    private String voucherId;

    @JsonProperty("nombre")
    private String voucherName;

    @JsonProperty("descripcion")
    private String voucherDescription;

    @JsonProperty("valorCanje")
    private String redemptionValue;

    @JsonProperty("tipoBeneficio")
    private String typeVoucher;

    @JsonProperty("banner")
    private String voucherBanner;

    @JsonProperty("idComercio")
    private String merchantId;
}
