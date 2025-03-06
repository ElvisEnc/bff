package bg.com.bo.bff.application.dtos.request.loans;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record Pcc01Request(
        @NotBlank(message = "El código de moneda no puede estar vacío")
        @Pattern(regexp = "\\d+", message = "El código de moneda debe ser numérico")
        @Schema(example = "840", description = "Este es el código de la moneda")
        String currency,

        @NotNull(message = "El monto no puede estar vacio")
        @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
        @Digits(integer = 12, fraction = 2, message = "El monto debe tener un máximo de 12 dígitos enteros y 2 decimales")
        @Schema(example = "10000", description = "Monto")
        BigDecimal amount) {
}
