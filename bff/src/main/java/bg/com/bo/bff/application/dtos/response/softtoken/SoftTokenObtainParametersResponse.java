package bg.com.bo.bff.application.dtos.response.softtoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenObtainParametersResponse {

    @Schema(description = "Tamaño del token")
    @JsonProperty("tokenSize")
    private Integer tokenSize;

    @Schema(description = "Duracion del token")
    @JsonProperty("tokenDuration")
    private Integer tokenDuration;

    @Schema(description = "Serial del dispositivo")
    @JsonProperty("serialNumber")
    private String serialNumber;

    @Schema(description = "Fecha de obtencion de los parametros")
    @JsonProperty("dateProcessed")
    private String dateProcessed;
}
