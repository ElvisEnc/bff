package bg.com.bo.bff.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchOfficeDataResponse {
    @Schema(example = "CBB", description = "Este es el branchCode")
    private String id;

    @Schema(example = "SUCURSAL COCHABAMBA", description = "Descripción de la sucursal")
    private String description;
}
