package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import bg.com.bo.bff.application.dtos.response.payment.service.ValidateAffiliateCriteriaResponse;
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
public class ValidateAffiliateCriteriaMWResponse {
    @Schema(description = "Código del servicio", example = "85")
    private String serviceCode;

    @Schema(description = "Criterios de búsqueda")
    private List<ValidateAffiliateCriteriaResponse.DataAffiliation> dataAffiliation;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor

    public static class DataAffiliation {

        @Schema(description = "Identificador", example = "1")
        private int identify;

        @Schema(description = "Nombre del titular", example = "Juan Pérez")
        private String nameOwner;

        @Schema(description = "Código de afiliación", example = "123456")
        private String code;

        @Schema(description = "Descripción", example = "Afiliado activo")
        private String description;

        @Schema(description = "Información adicional", example = "Servicio afiliado")
        private String additionalFact;

        @Schema(description = "Colección de registros de datos")
        private List<ValidateAffiliateCriteriaResponse.DataAffiliation.DataRegister> dataRegister;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DataRegister {

            @Schema(description = "Etiqueta", example = "Dato de entrada")
            private String label;

            @Schema(description = "Código de registro", example = "123456")
            private String value;

            @Schema(description = "Es mandatorio", example = "S")
            private String mandatory;

            @Schema(description = "Es editable", example = "S")
            private String edit;

            @Schema(description = "Código de grupo", example = "123")
            private String group;

            @Schema(description = "Descripción del campo", example = "campo")
            private String description;

            @Schema(description = "Código de campo", example = "123")
            private String code;
        }
    }
}
