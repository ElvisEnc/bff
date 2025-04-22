package bg.com.bo.bff.application.dtos.response.loyalty;

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
