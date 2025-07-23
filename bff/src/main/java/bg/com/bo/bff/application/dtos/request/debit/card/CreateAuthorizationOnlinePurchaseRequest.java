package bg.com.bo.bff.application.dtos.request.debit.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateAuthorizationOnlinePurchaseRequest {

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 5, message = "El monto mínimo es 5")
    @Max(value = 1500, message = "El monto máximo es 1500")
    @Schema(description = "Monto límite a transacciones", example = "5")
    private Integer amount;

    @JsonProperty
    @NotNull(message = "Periodos no válidos")
    private DCLimitsPeriod period;
}
