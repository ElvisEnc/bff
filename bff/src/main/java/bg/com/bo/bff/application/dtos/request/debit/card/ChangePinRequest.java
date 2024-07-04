package bg.com.bo.bff.application.dtos.request.debit.card;

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
public class ChangePinRequest {

    @NotNull(message = "El PIN es obligatorio")
    @Size(min = 1, max = 16, message = "El PIN debe tener entre 1 y 16 dígitos")
    @Schema(description = "Pin block generado por el cliente", example = "Q7498QWS133")
    private String pin;
}
