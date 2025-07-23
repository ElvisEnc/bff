package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyExchangeRateResponse {
    @JsonProperty("errorCode")
    private String codeError;

    @JsonProperty("errorMessage")
    private String message;

    @Schema(description = "data")
    private ExchangeResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExchangeResponse {

        @JsonProperty("pDecCompraTc")
        private Double purchaseFxRate ;

        @JsonProperty("pDecVentaTc")
        private Double saleFxRate ;

        @JsonProperty("pStrDescripcion")
        private String description;

        @JsonProperty("pStrCodError")
        private String code;

        @JsonProperty("pStrDesError")
        private String messageError;
    }
}
