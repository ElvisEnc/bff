package bg.com.bo.bff.application.dtos.response.user.apiface;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDetail {
    @Schema(description = "Id del distrito", example = "1")
    private Integer id;

    @Schema(description = "Nombre del distrito", example = "Santa Cruz")
    private String description;
}
