package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record FeePrepaidCardResponse(
        @Schema(example = "100.5", description = "Monto de la comisión por avance de efectivo de una cuenta de tarjeta de crédito")
        BigDecimal fee,
        @Schema(example = "50.5", description = "Monto de la comisión por seguro de una cuenta de tarjeta de crédito")
        BigDecimal amount
) {
}
