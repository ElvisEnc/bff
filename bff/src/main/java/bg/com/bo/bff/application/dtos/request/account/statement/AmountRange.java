package bg.com.bo.bff.application.dtos.request.account.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountRange {
    @DecimalMin(value = "0.0", message = "El monto mínimo debe ser al menos 0")
    @DecimalMax(value = "9999999.99", message = "El monto mínimo debe ser como máximo 9999999.99")
    @Schema(example = "0.50", description = "Monto mínimo")
    private BigDecimal min;

    @DecimalMin(value = "0.0", message = "El monto máximo debe ser al menos 0")
    @DecimalMax(value = "9999999.99", message = "El monto máximo debe ser como máximo 9999999.99")
    @Schema(example = "1000.00", description = "Monto máximo")
    private BigDecimal max;
}
