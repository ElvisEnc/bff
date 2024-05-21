package bg.com.bo.bff.providers.dtos.responses.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMWFactorDataResponse {
    @Schema(example = "1234567889900", description = "Este es el código de persona del cliente ganamóvil")
    private String personId;

    @Schema(example = "11", description = "Código de la imagen")
    private String codeImage;

    @Schema(example = "2", description = "Segundo factor")
    private String secondFactor;

    @Schema(example = "XX-XX-XX", description = "UUID")
    private String idGeneratorUuid;
}
