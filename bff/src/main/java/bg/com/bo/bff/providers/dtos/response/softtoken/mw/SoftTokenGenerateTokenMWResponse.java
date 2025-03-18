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
public class SoftTokenGenerateTokenMWResponse {

    @Schema(description = "Codigo de respuesta")
    private String codeError;

    @Schema(description = "Es el token generado")
    private String codeToken;

    @Schema(description = "Tiempo de duracion del token")
    private Integer durationToken;

}
