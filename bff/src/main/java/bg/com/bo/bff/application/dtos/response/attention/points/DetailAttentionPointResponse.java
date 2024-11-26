package bg.com.bo.bff.application.dtos.response.attention.points;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailAttentionPointResponse {
    private List<DetailPointResponse> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetailPointResponse {
        @Schema(description = "Identificador del punto de atención")
        private int pointId;

        @Schema(description = "Identificador de la referencia del punto de atención ")
        private String referenceId;

        @Schema(description = "Identificador de la propiedad")
        private int propertyId;

        @Schema(description = "Dirección de la ubicación del punto de atención")
        private String address;

        @Schema(description = "Teléfono del punto de atención")
        private String phone;

        @Schema(description = "Descripción de la plantilla del punto")
        private String description;

        @Schema(description = "Identificador de la clave")
        private String type;
    }
}
