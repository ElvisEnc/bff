package bg.com.bo.bff.application.dtos.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBiometricsRequest {
    @Schema(example = "true", description = "nuevo estado de la biometría")
    private Boolean status;

    @NotBlank
    @Schema(example = "dfkjdsfg65454dgdsgs", description = "Token de la biometría")
    private String tokenBiometric;

    @NotBlank
    @Max(value = 5)
    @Min(value = 1, message = "El tipo debe ser mayor a 0")
    @Pattern(regexp = "\\d+", message = "Tipo de Autenticación debe ser solo número")
    @Schema(example = "5", description = "Tipo de Autenticación, 4=finger 5=facial")
    private String typeAuthentication;
}

