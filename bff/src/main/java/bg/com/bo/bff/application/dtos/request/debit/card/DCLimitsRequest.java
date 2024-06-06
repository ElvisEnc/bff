package bg.com.bo.bff.application.dtos.request.debit.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DCLimitsRequest {
    @NotNull(message = "El monto límite es obligatorio")
    @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 13, fraction = 0, message = "El monto puede tener hasta 13 dígitos enteros")
    @Schema(description = "Monto límite a transacciones", example = "100")
    private BigDecimal dailyAmount;

    @NotNull(message = "La cantidad máxima de extracciones es obligatoria")
    @NotBlank(message = "La cantidad máxima de extracciones no puede estar vacía")
    @Pattern(regexp = "\\d+", message = "La cantidad máxima de extracciones solo permite números")
    @Schema(description = "Cantidad máxima de extracciones diarias", example = "50")
    private String dailyCount;

    @Valid
    @JsonProperty
    @NotNull(message = "Periodos no válidos")
    private DCLimitsPeriod period;
}
