package bg.com.bo.bff.providers.dtos.request.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiPersonalInformationNetRequest {
    @JsonProperty("intNumeroPersona")
    private String intNumeroPersona;

    @JsonProperty("pStrNroSesion")
    private String pStrNroSesion;
}
