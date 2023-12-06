package bg.com.bo.bff.model;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
public class LoginRequest {
    @Schema(example = "1234567", description = "Este es el carnet del usuario")
    private String cedula;

    @Schema(example = "a1", description = "Este es el complemento del carnet")
    private String complemento;

    @Schema(example = "abc123", description = "Este es la contraseña")
    private String password;

    @Schema(example = "GANAMOVIL", description = "Este es el canal")
    private String canal;
}
