package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    @Pattern(regexp = "^[0-9]+$", message = "El tipo debe contener solo números")
    @Min(value = 1, message = "El tipo debe ser mayor a 0")
    @Max(value = 10, message = "El tipo debe ser de dos dígitos como máximo")
    @Schema(example = "1", description = "Corresponde al tipo de credenciales para el login. 1=Alias; 2=Código de persona; 3=DNI;")
    private String type;

    @NotBlank
    @Schema(example = "1234567", description = "El user correspondiente al tipo")
    private String user;

    @Schema(example = "a1", description = "Complemento del DNI")
    private String complement;

    @NotNull
    @Schema(example = "1234", description = "Contraseña")
    private String password;

    @NotNull
    @Schema(example = "d345g3d54fg35d4gdf", description = "Token Biometrico")
    private String tokenBiometric;
}
