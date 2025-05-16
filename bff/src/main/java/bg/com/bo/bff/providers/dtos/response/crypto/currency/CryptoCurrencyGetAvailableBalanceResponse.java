package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGetAvailableBalanceResponse {

    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @Schema(description = "data")
    @JsonProperty("data")
    private GetBalanceResponse data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetBalanceResponse {

        @JsonProperty("pStrMoneda")
        private String currency;

        @JsonProperty("pDecSaldoDisponible")
        private Double availableBalance;

        @JsonProperty("pIntNumeroCuenta")
        private Integer account;

        @JsonProperty("pStrEstado")
        private String status;

        @JsonProperty("pIntJtsOid")
        private Integer jtsOid;

        @JsonProperty("pStrProducto")
        private String product;

        @JsonProperty("pStrCodError")
        private String codeError;

        @JsonProperty("pStrDesError")
        private String description;
    }
}
