package bg.com.bo.bff.providers.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGetAvailableBalanceResponse {

    @Schema(description = "errorCode")
    private String codeError;

    @Schema(description = "errorMessage")
    private String message;

    @Schema(description = "data")
    private GetBalanceResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetBalanceResponse {

        @Schema(description = "pStrMoneda")
        private String currency;

        @Schema(description = "pDecSaldoDisponible")
        private BigDecimal availableBalance;

        @Schema(description = "pIntNumeroCuenta")
        private Integer account;

        @Schema(description = "pStrEstado")
        private String status;

        @Schema(description = "pStrCodError")
        private String codeError;

        @Schema(description = "pStrDesError")
        private String description;
    }
}
