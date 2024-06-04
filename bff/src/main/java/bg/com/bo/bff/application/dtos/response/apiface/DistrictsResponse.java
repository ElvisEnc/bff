package bg.com.bo.bff.application.dtos.response.apiface;

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
public class DistrictsResponse {
    @Schema(description = "Estructura de respuesta lista de distritos")
    private List<DistrictDetail> data;
}
