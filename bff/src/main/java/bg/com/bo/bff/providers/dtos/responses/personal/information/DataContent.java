package bg.com.bo.bff.providers.dtos.responses.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataContent {
    @JsonProperty("cur_datosClienteGanasueldo")
    private List<ClientData> clientDataList;

    @JsonProperty("cur_referenciasPersonaFisica")
    private List<Reference> references;

    @JsonProperty("cur_actividadEconomica")
    private List<EconomyActivity> economicActivities;
}
