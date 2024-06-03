package bg.com.bo.bff.application.dtos.response.user;

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
public class EconomicActivityResponse {
    @Schema(description = "Listado de la Actividad Economica del usuario")
    private List<EconomicalActivity> economicActivity;

    @Schema(description = "Listado de Nivel de Ingreso del usuario")
    private List<EconomicalActivity> incomeLevel;

    @Schema(description = "Listado de Fuente de Ingreso del usuario")
    private List<EconomicalActivity> incomeSource;

    @Schema(description = "Listado de Cargo o Puesto de trabajo")
    private List<EconomicalActivity> jobTitle;
}
