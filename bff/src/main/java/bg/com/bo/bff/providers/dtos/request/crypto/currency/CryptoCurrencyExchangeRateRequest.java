package bg.com.bo.bff.providers.dtos.request.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyExchangeRateRequest {

    @JsonProperty("personNumber")
    private Integer personNumber;

    @JsonProperty("currencyCode")
    private Integer currencyCode;
}
