package bg.com.bo.bff.application.dtos.request.user;


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
public class UpdateBiometricsRequest {
    @NotNull(message = "El estado de la biometría no puede ser nulo")
    @Schema(example = "true", description = "Nuevo estado de la biometría")
    private Boolean status;

    @NotNull
    @Pattern(regexp = "^\\S*$")
    @Size(min = 1, max = 1000)
    @Schema(example = "dfkjdsfg65454dgdsgs", description = "Token de la biometría")
    private String tokenBiometric;

    @NotBlank
    @Pattern(regexp = "[1-5]", message = "El tipo de autenticación debe ser un número entre 1 y 5")
    @Schema(example = "5", description = "Tipo de Autenticación, 2=contraseña 4=finger 5=facial")
    private String typeAuthentication;
}