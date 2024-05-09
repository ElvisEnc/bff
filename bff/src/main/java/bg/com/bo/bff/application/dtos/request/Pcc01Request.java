package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
public class Pcc01Request {
    @NotBlank(message = "El código de moneda no puede estar vacío")
    @Schema(example = "840", description = "Este es el código de la moneda")
    private String currency;

    @Min(value = 1, message = "El monto debe ser por lo menos 1")
    @Schema(example = "10000", description = "Monto")
    private Double amount;
}
