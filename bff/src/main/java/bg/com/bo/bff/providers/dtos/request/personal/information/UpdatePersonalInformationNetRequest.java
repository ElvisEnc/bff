package bg.com.bo.bff.providers.dtos.request.personal.information;


import bg.com.bo.bff.providers.dtos.response.personal.information.ClientData;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdatePersonalInformationNetRequest {

    @JsonProperty("pStrNroSesion")
    private String sessionNumber;
    @JsonProperty("intNumeroPersona")
    private String personId;
    @JsonProperty("datosNuevos")
    private UpdateDataPerson newData;
    @JsonProperty("datosAntiguos")
    private List<ClientData> oldData;
    @JsonProperty("referencias")
    private List<PersonalReferences> personalReferences;
    @JsonProperty("canal")
    private String channel;


}



