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
public class LoyaltyVoucherResponse {

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
