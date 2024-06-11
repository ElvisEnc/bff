package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Pcc01Request {
    @NotBlank(message = "El código de moneda no puede estar vacío")
    @Schema(example = "840", description = "Este es el código de la moneda")
    private String currency;

    @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Schema(example = "10000", description = "Monto")
    private BigDecimal amount;
}
