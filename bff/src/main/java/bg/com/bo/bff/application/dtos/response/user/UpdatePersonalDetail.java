package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class UpdatePersonalDetail {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MaritalStatus {
        @Schema(description = "Estado civil", example = "S")
        private String status;

        @Schema(description = "Apellido del cónyuge", example = "Pérez")
        private String spouseLastName;

        @Schema(description = "Nombre del cónyuge", example = "Juan")
        private String spouseName;

        @Schema(description = "Usa el nombre del esposo", example = "S o N")
        private String usesSpouseLastName;;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EconomicalActivity {
        @Schema(description = "Tipo de actividad económica", example = "65191")
        private String economicActivityCategory;

        @Schema(description = "Nombre de la empresa", example = "Empresa S.A.")
        private String company;

        @Schema(description = "Cargo en la empresa", example = "Gerente")
        private String position;

        @Schema(description = "Nivel de ingresos", example = "Menos de $ 600")
        private String incomeLevel;

        @Schema(description = "Rubro actividad economica", example = " Industria manufacturera")
        private String category;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData {


        @Schema(description = "Número de teléfono", example = "123456789")
        private String telephoneNumber;

        @Schema(description = "Correo electrónico", example = "ejemplo@dominio.com")
        private String email;

        @Schema(description = "Sección", example = "3")
        private String section;

        @Schema(description = "Zona", example = "4")
        private String zone;

        @Schema(description = "Distrito", example = "Distrito Central")
        private String dictrict;

        @Schema(description = "Dirección", example = "Calle Falsa 123")
        private String address;

        @Schema(description = "Departamento", example = "Central")
        private String department;

        @Schema(description = "Codigo Departamento", example = "Central")
        private String departmentCode;


        @Schema(description = "Ciudad", example = "Central")
        private String city;

        @Schema(description = "Codigo Departamento", example = "Central")
        private String cityCode;

        @Schema(description = "Piso", example = "5")
        private String floor;

        @Schema(description = "Coordenadas GPS", example = "40.7128,-74.0060")
        private String GPS;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reference {
        @Schema(description = "Nombre de la referencia", example = "María")
        private String name;
        @Schema(description = "Teléfono de la referencia", example = "987654321")
        private String phones;
        @Schema(description = "Tipo de referencia", example = "P")
        private String referenceType;
        @Schema(description = "Ordinal de referencia", example = "P")
        private String ordinal;
        @Schema(description = "Tipo de persona", example = "F")
        private String personType;
        @Schema(description = "Relacion personal", example = "2")
        private String relationship;
    }
}
