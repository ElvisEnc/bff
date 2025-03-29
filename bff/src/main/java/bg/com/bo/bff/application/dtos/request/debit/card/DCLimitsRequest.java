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
    @DecimalMin(value = "500", inclusive = true, message = "El monto debe ser al menos 500")
    @DecimalMax(value = "10000", inclusive = true, message = "El monto debe ser como máximo 10000")
    @Digits(integer = 5, fraction = 2, message = "El monto puede tener hasta 4 dígitos enteros y 2 decimales")
    @Schema(description = "Monto límite a transacciones", example = "750.00")
    private BigDecimal dailyAmount;

    @NotNull(message = "La cantidad máxima de extracciones es obligatoria")
    @Min(value = 1, message = "La cantidad máxima de extracciones debe ser al menos 1")
    @Max(value = 10, message = "La cantidad máxima de extracciones debe ser como máximo 10")
    @Schema(description = "Cantidad máxima de extracciones diarias", example = "5")
    private Integer dailyCount;

    @Valid
    @JsonProperty
    @NotNull(message = "Periodos no válidos")
    private DCLimitsPeriod period;
}
