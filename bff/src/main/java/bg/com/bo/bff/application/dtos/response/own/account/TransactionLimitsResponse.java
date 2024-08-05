package bg.com.bo.bff.application.dtos.response.own.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLimitsResponse {
        @Schema(description = "Cantidad de retiros por dia.")
        private Integer countLimit;

        @Schema(description = "Cantidad de retiros registrados.")
        private Integer countLimitRegistered;

        @Schema(description = "Monto disponible permitido por transacción.")
        private Integer amountLimit;

        @Schema(description = "Monto disponible permitido por grupo.")
        private Integer amountLimitGroup;

        @Schema(description = "Código de moneda.")
        private String currencyCode;

        @Schema(description = "Tipo de límite.")
        private String type;
}
