package bg.com.bo.bff.application.dtos.request.registry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistryOldDeviceIdentificatorRequest {
    @Valid
    @NotBlank(message = "Invalid channel.")
    @JsonProperty(required=false)
    @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String channel;

    @Valid
    @NotBlank(message = "Empty emei.")
    @JsonProperty(required = true)
    @Schema(example = "ae854f65b8a5485c", description = "Imei del dispositivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imei;

    @Valid
    @NotBlank(message = "Invalid model.")
    @JsonProperty(required = true)
    @Schema(example = "SM-G780G", description = "Modelo del dispositivo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String model;

    @Valid
    @NotBlank(message = "Invalid os.")
    @JsonProperty(required = true)
    @Schema(example = "ANDROID", description = "Sistema operativo del dispositivo.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String os;

    @Valid
    @NotBlank(message = "Invalid id.")
    @JsonProperty(required = true)
    @Schema(example = "766f78354299183e", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Valid
    @NotBlank(message = "Invalid rsid.")
    @JsonProperty(required = true)
    @Schema(example = "766f78354299183e", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rsid;

    @Valid
    @NotBlank(message = "Invalid version.")
    @JsonProperty(required = true)
    @Schema(example = "v2.4.52", description = "Versión de la aplicación del dispositivo.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Valid
    @NotBlank(message = "Invalid didbga.")
    @JsonProperty(required = true)
    @Schema(example = "b74765cf37f7c564", requiredMode = Schema.RequiredMode.REQUIRED)
    private String didbga;

    @Valid
    @NotBlank(message = "Invalid ksBga.")
    @JsonProperty(required = true)
    @Schema(example = "c9f4af9051769979", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ksBga;
}
