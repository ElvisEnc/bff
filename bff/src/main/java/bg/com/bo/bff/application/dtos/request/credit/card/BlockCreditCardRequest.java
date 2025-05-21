package bg.com.bo.bff.application.dtos.request.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockCreditCardRequest {
    @NotBlank()
    @Size(min = 1, max = 19)
    @Pattern(regexp = "13-\\d{2}-\\d{2}-\\d{1,10}", message = "Se espera el formato correcto.")
    @Schema(description = "número compuesto de la tarjeta", example = "13-07-10-000579")
    private String cmsCard;

    @NotBlank()
    @Pattern(regexp = "^[2|0]$")
    @Size(min = 1, max = 1)
    @Schema(description = "bloquedo o desbloqueo. 0=desbloqueo, 2=bloqueo", example = "0")
    private String type;

    @NotBlank(message = "Tipo de tarjeta no puede ser vacia. TC=Tarjeta de credito, TP=Tarjeta prepapagada")
    @Pattern(regexp = "^(TC|TP)$")
    @Schema(description = "Tipo de tarjeta. TC=Tarjeta de credito, TP=Tarjeta prepapagada", example = "TC")
    private String typeCard;

}
