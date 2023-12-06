package bg.com.bo.bff.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @Schema(example = "SUCCESS", description = "Este es el código de respuesta exitosa")
    private String statusCode;

    @Schema(description = "Estos son los datos del usuario")
    private User data;
}
