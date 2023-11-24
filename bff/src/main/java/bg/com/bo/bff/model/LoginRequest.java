package bg.com.bo.bff.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(example = "1234567", description = "Este es el carnet del usuario")
    private String cedula;

    @Schema(example = "a1", description = "Este es el complemento del carnet")
    private String complemento;

    @Schema(example = "abc123", description = "Este es la contraseña")
    private String password;
}
