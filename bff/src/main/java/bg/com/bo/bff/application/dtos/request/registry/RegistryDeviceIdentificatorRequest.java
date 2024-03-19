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
public class RegistryDeviceIdentificatorRequest {
    @Valid
    @NotBlank(message = "Invalid boot.")
    @JsonProperty(required = true)
    @Schema(example = "mw8998-002.0069.00", description = "Número de version del bootloader del sistema del dispotivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String boot;

    @Valid
    @NotBlank(message = "Invalid uniqueId.")
    @JsonProperty(required = true)
    @Schema(example = "FCDBD8EF-62FC-4ECB-B2F5-92C9E79AC7F9", description = "ID unico del dispositivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uniqueId;

    @Valid
    @NotBlank(message = "Invalid soCodeName.")
    @JsonProperty(required = true)
    @Schema(example = "iPhone7.2", description = "Código del sistema operativo.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String soCodeName;

    @Valid
    @NotBlank(message = "Invalid systemName.")
    @JsonProperty(required = true)
    @Schema(example = "iPhone OS", description = "Nombre del OS del dispositivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String systemName;

    @Valid
    @NotBlank(message = "Invalid systemVersion.")
    @JsonProperty(required = true)
    @Schema(example = "Version del OS del dispositivo", description = "11.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private String systemVersion;

    @Valid
    @NotBlank(message = "Invalid systemBuildId.")
    @JsonProperty(required = true)
    @Schema(example = "12A269", description = "Build number del sistema operativo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String systemBuildId;

    @Valid
    @NotBlank(message = "Invalid userAgent.")
    @JsonProperty(required = true)
    @Schema(example = "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143", description = "User Agent del dispositivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userAgent;

    @Valid
    @NotNull(message = "Invalid firstInstallTime.")
    @JsonProperty(required = true)
    @Schema(example = "1517681764528", description = "Momento en el tiempo en el que la aplicacion fue instalada por primera vez, en milisegundos.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long firstInstallTime;

    @Valid
    @NotNull(message = "Invalid firstInstallTime.")
    @JsonProperty(required = true)
    @Schema(example = "false", description = "Especifica si la aplicación se esta ejecutando en modo debug.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean debug;

    @Valid
    @NotNull(message = "Invalid firstInstallTime.")
    @JsonProperty(required = true)
    @Schema(example = "false", description = "Especifica si la aplicacion se esta ejecutando en un emulador.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean emulator;

    @Valid
    @NotBlank(message = "Invalid app version.")
    @JsonProperty(required = true)
    @Schema(example = "3.41.4", description = "Versión de la aplicación.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appVersion;
}
