package bg.com.bo.bff.providers.dtos.response.apiface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentNetDetail {
    @JsonProperty("pCodError")
    private String codeError;

    @JsonProperty("pDesError")
    private String descriptionError;

    @JsonProperty("pIntCodRespuesta")
    private int codeResult;

    @JsonProperty("pStrDescripcion")
    private String description;

    @JsonProperty("pStrMensajeApp")
    private String messageApp;

    @JsonProperty("pStrMensajeUsuario")
    private String messageUser;

    @JsonProperty("pStrNroSesion")
    private String sessionNumber;

    @JsonProperty("pintCodPlaza")
    private int codeSquare;
}
