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
public class ListAttentionPointsResponse {
    private List<AttentionPoint> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttentionPoint {
        @Schema(description = "Identificador del punto de atención")
        private int pointId;
        @Schema(description = "Identificador de la referencia del punto de atención ")
        private String referenceId;
        @Schema(description = "Tipo de punto de atención")
        private String type;
        @Schema(description = "Nombre del punto de atención")
        private String name;
        @Schema(description = "Latitud del punto de atención")
        private String latitude;
        @Schema(description = "Longitud del punto de atención")
        private String longitude;
        @Schema(description = "Oficina central")
        private Boolean central;
        @Schema(description = "Es depositario")
        private Boolean depositary;
        @Schema(description = "Descripción del punto de atención")
        private String description;
        @Schema(description = "Teléfono del punto de atención")
        private String phone;
        @Schema(description = "Dirección de la ubicación del punto de atención")
        private String address;
        @Schema(description = "Código de la plaza")
        private int cityCode;
        @Schema(description = "Subtipo de punto de atención")
        private String subType;
        @Schema(description = "Es para discapacitados")
        private Boolean handicapped;
        @Schema(description = "Horario de atención")
        private Boolean openAllDay;
        @Schema(description = "Código de Moneda")
        private String currencyCode;
    }
}
