package bg.com.bo.bff.providers.dtos.requests.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalInformationNetRequest {
    @JsonProperty("intNumeroPersona")
    private String intNumeroPersona;

    @JsonProperty("pStrNroSesion")
    private String pStrNroSesion;
}
