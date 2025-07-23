package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableCreditCardResponse {
    @Schema(description = "monto disponible")
    private BigDecimal availableAmount;

    @Schema(description = "monto límite")
    private BigDecimal limitAmount;
}
