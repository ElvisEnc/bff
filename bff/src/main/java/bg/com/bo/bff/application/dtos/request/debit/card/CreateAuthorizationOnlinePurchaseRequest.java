package bg.com.bo.bff.application.dtos.request.debit.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CreateAuthorizationOnlinePurchaseRequest {

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "5", inclusive = false, message = "El monto debe ser mayor que cero")
    @DecimalMax(value = "1500", inclusive = false, message = "El monto debe ser menor o igual a 1500")
    @Digits(integer = 13, fraction = 0, message = "El monto puede tener hasta 13 dígitos enteros")
    @Schema(description = "Monto límite a transacciones", example = "100")
    private BigDecimal amount;

    @JsonProperty
    @NotNull(message = "Periodos no válidos")
    private DCLimitsPeriod period;
}
