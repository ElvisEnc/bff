package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@lombok.Data
public class LoginRequest {
    @Schema(example = "1", description = "Corresponde al tipo de credenciales para el login. 1=Alias; 2=Código de persona; 3=DNI;")
    private @NotNull String type;

    @Schema(example = "1234567", description = "El user correspondiente al tipo")
    private @NotBlank String user;

    @Schema(example = "a1", description = "Complemento del DNI")
    private String complement;

    @Schema(example = "1234", description = "Contraseña")
    private @NotBlank String password;

    @Schema(example = "3.2.0", description = "Especifica la versión de la app.")
    private String appVersion;

    private Device deviceIdentification;
}
