package bg.com.bo.bff.application.dtos.response.registry;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class RegistryResponse {
    @Schema(example = "1234", description = "Person ID del usuario.")
    private Long personId;

    @Schema(example = "MIICIjANBgkqhki...", description = "Llave pública de encriptación de la aplicación.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String appKey;
}
