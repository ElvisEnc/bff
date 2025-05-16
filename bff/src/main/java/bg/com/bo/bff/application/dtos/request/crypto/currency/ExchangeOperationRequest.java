package bg.com.bo.bff.application.dtos.request.crypto.currency;

import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import bg.com.bo.bff.commons.annotations.generics.ValidAmount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOperationRequest {

    @ValidAmount
    private Double amount;

    @NotNull(message = "La moneda es obligatoria")
    @NotBlank(message = "La moneda no puede estar vacía")
    @Pattern(regexp = "\\d+", message = "La moneda debe contener solo números")
    @Schema(description = "Código de la moneda", example = "068")
    private String currency;

    @NotBlank(message = "destination account no puede ser vacío.")
    @Schema(description = "destination account indica la cuenta de destino.",
            example = "558745")
    private String destinationAccount;

    @NotBlank(message = "La descripción no puede estar vacía")
    @NotNull(message = "Descripción no válida")
    @Size(min = 3, max = 50, message = "La descripción debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Descripción de la transferencia", example = "Pago de servicios")
    @DescriptionChars
    private String description;

}
