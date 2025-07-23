package bg.com.bo.bff.application.dtos.request.registry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistryCredentialsRequest {
    @Valid
    @NotNull(message = "Invalid personId.")
    @JsonProperty(required = true)
    @Schema(example = "12345", description = "Person ID del usuario.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long personId;

    @Valid
    @NotBlank(message = "Invalid password format.")
    @JsonProperty(required = true)
    @Schema(example = "1234", description = "Contraseña del usuario.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Valid
    @NotBlank(message = "Invalid password format.")
    @JsonProperty(required = true)
    @Schema(example = "1234", description = "Contraseña biometria del usuario.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String passwordBiometric;

    @Valid
    @NotNull(message = "Invalid type auth.")
    @JsonProperty(required = true)
    @Schema(example = "1", description = "Tipo de login [Password|Biometría]", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer type;
}
