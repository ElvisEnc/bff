package bg.com.bo.bff.application.dtos.request.destination.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    @Schema(example = "1", description = "Número de página")
    @NotNull(message = "page es obligatorio")
    @Min(value = 1, message = "Page debe comenzar por lo menos en 1")
    private Integer page;

    @Schema(example = "10", description = "Cantidad de registros por página")
    @NotNull(message = "pageSize es obligatorio")
    @Min(value = 1, message = "pageSize debe comenzar por lo menos en 1")
    private Integer pageSize;
}
