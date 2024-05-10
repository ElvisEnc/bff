package bg.com.bo.bff.application.dtos.request.qr;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QrListOrderRequest {
    @NotBlank(message = "field no puede estar en vacio")
    @Schema(example = "REGISTRATION_DATE | EXPIRATION_DATE | AMOUNT", description = "Campo por el cual se va filtrar en orden ascendente o descendente, por defecto REGISTRATION_DATE")
    private String field;

    @NotNull(message = "desc no puede ser nulo")
    @Schema(example = "true", description = "true = Descendente, de hoy al más antiguo. false = Ascendente, Del más antiguo a hoy")
    private Boolean desc;
}


