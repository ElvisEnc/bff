package bg.com.bo.bff.providers.dtos.response.softtoken.mw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenObtainParametersMWResponse {
    @Schema(description = "Codigo de respuesta")
    private String codeError;

    @Schema(description = "Tamaño del token a generar")
    private Integer tokenSize;

    @Schema(description = "Tiempo de duracion del token")
    private Integer tokenDuration;

    @Schema(description = "Serial del dispositivo")
    private String serialNumber;

    @Schema(description = "FEcha del proceso")
    private String processDate;
}
