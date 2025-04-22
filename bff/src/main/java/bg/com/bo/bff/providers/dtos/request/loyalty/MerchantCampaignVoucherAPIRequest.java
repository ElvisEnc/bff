package bg.com.bo.bff.providers.dtos.request.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerchantCampaignVoucherAPIRequest {
    @JsonProperty("codigoCampana")
    private int campaignId;

    @JsonProperty("idComercio")
    private UUID merchantId;

    @JsonProperty("idCategoria")
    private UUID categoryId;
}
