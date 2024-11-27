package bg.com.bo.bff.application.dtos.request.login;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    @Pattern(regexp = "[1-5]", message = "El tipo de autenticación debe ser un número entre 1 y 5")
    @Min(value = 1, message = "El tipo debe ser mayor a 0")
    @Max(value = 5, message = "El tipo debe ser de dos dígitos como máximo")
    @Schema(example = "1", description = "Corresponde al tipo de credenciales para el login. 1=Alias; 2=Código de persona; 3=DNI;")
    private String type;

    @NotNull
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
