package bg.com.bo.bff.providers.dtos.request.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyExchangeOperationRequest {

    @JsonProperty("importe")
    private BigDecimal amount;

    @JsonProperty("codMoneda")
    private Integer currencyCode;

    @JsonProperty("nroJtsOidOrigen")
    private Integer accountId;

    @JsonProperty("nroJtsOidDestino")
    private Integer destinationAccount;

    @JsonProperty("glosa")
    private String description;

    @JsonProperty("nroSolicitud")
    private Integer requestNumber;

    @JsonProperty("canal")
    private int canal;
}
