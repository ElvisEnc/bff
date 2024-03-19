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
public class RegistryRequest {
    @Valid
    @NotNull(message = "Invalid credentials.")
    private RegistryCredentialsRequest credentials;

    @Valid
    @NotNull(message = "Invalid device identificator.")
    private RegistryDeviceIdentificatorRequest deviceIdentificator;

    @Valid
    @NotNull(message = "Invalid old device identificator.")
    private RegistryOldDeviceIdentificatorRequest oldDeviceIdentificator;

    @Valid
    @NotBlank(message = "Invalid user key.")
    @JsonProperty(required = true)
    @Schema(example = "MIICIjANBgkqhki...", description = "Llave pública de encriptación del usuario.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userKey;

    @JsonProperty(required = true)
    @NotBlank(message = "Invalid geo reference.")
    @Schema(example = "12.12312,12.23223", description = "Datos de geolocalización.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String geoReference;
}
