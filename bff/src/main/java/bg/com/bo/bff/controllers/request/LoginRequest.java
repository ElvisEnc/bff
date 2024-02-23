package bg.com.bo.bff.controllers.request;

import bg.com.bo.bff.model.enums.LoginSchemaName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@lombok.Data
public class LoginRequest {
    @Schema(example = "PERSONIDLOGIN", description = "Corresponde al tipo de credenciales aportadas para el login.")
    private @NotNull LoginSchemaName type;

    @Schema(example = "1234567", description = "Este es el carnet del usuario")
    private String cedula;

    @Schema(example = "a1", description = "Este es el complemento del carnet")
    private String complemento;

    @Schema(example = "1", description = "PersonId correspondiente al cliente.")
    private String personId;

    @Schema(example = "abc123", description = "Este es la contraseña")
    private @NotBlank String password;

    @Schema(example = "34D01C25-4503-43F5-997B-1464AD1880A4", description = "Es el ID único generado en el enrolamiento.")
    private @NotBlank String deviceId;
}
