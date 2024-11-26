package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeePrepaidCardMWResponse {

    @JsonProperty("transferFee")
    private BigDecimal transferFee;
    @JsonProperty("insuranceAmount")
    private BigDecimal insuranceAmount;
}
