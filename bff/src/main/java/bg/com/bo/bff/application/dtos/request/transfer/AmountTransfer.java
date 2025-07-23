package bg.com.bo.bff.application.dtos.request.transfer;

import bg.com.bo.bff.commons.annotations.generics.ValidAmount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AmountTransfer {
    @NotNull(message = "La moneda es obligatoria")
    @NotBlank(message = "La moneda no puede estar vacía")
    @Pattern(regexp = "\\d+", message = "La moneda debe contener solo números")
    @Schema(description = "Código de la moneda", example = "068")
    private String currency;

    @ValidAmount
    private BigDecimal amount;
}
