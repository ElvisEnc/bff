package bg.com.bo.bff.application.dtos.request.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeePrepaidCardRequest {
    @NotBlank
    @Pattern(regexp = "13-\\d{2}-\\d{2}-\\d{2}", message = "Valor inválido para cmsAccountNumber")
    @Schema(description = "número compuesto de la Cuenta", example = "123456")
    private String cmsAccount;

    @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
    @Schema(description = "monto", example = "10.00")
    private BigDecimal amount;
}
