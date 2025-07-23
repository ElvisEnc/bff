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
public class OrderRequest {
    @NotBlank(message = "field no puede estar en vacio")
    @Schema(example = "FIELD", description = "Campo por el cual se va filtrar")
    private String field;

    @NotNull(message = "desc no puede ser nulo")
    @Schema(example = "true", description = "true = Descendente, de hoy al más antiguo | z -> a --- false = Ascendente, Del más antiguo a hoy | a -> z")
    private Boolean desc;
}