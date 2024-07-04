package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateCriteriaResponse {
    @Schema(description = "Código del servicio", example = "85")
    private String serviceCode;

    @Schema(description = "Año", example = "2024")
    private int year;

    @Schema(description = "Subservicios")
    private List<SubService> subServices;

    @Schema(description = "Criterios de búsqueda")
    private List<SearchCriteria> criteria;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubService {

        @Schema(description = "Etiqueta del criterio", example = "Etiqueta del criterio")
        private String criteriaLabel;

        @Schema(description = "Abreviatura", example = "Abreviatura")
        private String abbreviation;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCriteria {

        @Schema(description = "Etiqueta del criterio", example = "CÓDIGO")
        private String labelCriteria;

        @Schema(description = "Descripción", example = "Descripción del criterio")
        private String description;

        @Schema(description = "ID del criterio de búsqueda", example = "9461")
        private String searchCriteriaId;

        @Schema(description = "Abreviatura del ID del criterio de búsqueda", example = "9461")
        private String searchCriteriaIdAbbreviation;

        @Schema(description = "Campos del criterio de búsqueda")
        private List<Field> fields;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Field {

            @Schema(description = "Identificador", example = "9461")
            private int identifier;

            @Schema(description = "Etiqueta", example = "Cuenta")
            private String label;

            @Schema(description = "Descripción", example = "CUENTA")
            private String description;

            @Schema(description = "Abreviatura", example = "2")
            private String abbreviation;

            @Schema(description = "Es obligatorio", example = "S")
            private String isMandatory;

            @Schema(description = "Longitud mínima", example = "1")
            private String minimumLength;

            @Schema(description = "Longitud máxima", example = "10")
            private String maximumLength;

            @Schema(description = "Código del tipo de dato", example = "1")
            private String dataTypeCode;

            @Schema(description = "Valores")
            private List<Value> values;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Value {
                @Schema(description = "Código", example = "1")
                private String code;

                @Schema(description = "Descripción", example = "Descripción")
                private String description;
            }
        }
    }
}
