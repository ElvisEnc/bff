package bg.com.bo.bff.application.dtos.request.commons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteriaRequest {

    @Schema(example = "[PARAMETER]", description = "Parametros de busqueda")
    private List<String> parameters;

    @Schema(example = "Buscar por...", description = "Valor de busqueda aplicado por parametro")
    private String value;
}
