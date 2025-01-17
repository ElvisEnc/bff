package bg.com.bo.bff.providers.dtos.response.remittance.mw;

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
public class GPCurrenciesMW {

        @JsonProperty("currencyCode")
        private int currencyCode;
        @JsonProperty("currencyAbbr")
        private String currencyAbbr;
        @JsonProperty("commonPurchaseRate")
        private BigDecimal commonPurchaseRate;
        @JsonProperty("currencyDescription")
        private String currencyDescription;
        @JsonProperty("currencyType")
        private char currencyType;
}
