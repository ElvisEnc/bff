package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class PersonalDetail {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MaritalStatus {
        @Schema(description = "Estado civil", example = "S")
        private String status;

        @Schema(description = "Apellido del cónyuge", example = "Pérez")
        private String husbandLastName;

        @Schema(description = "Nombre del cónyuge", example = "Juan")
        private String spouseName;

        @Schema(description="Usa el apellido del esposo")
        private String hasHusbandLastName;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EconomicalActivity {
        @Schema(description = "Tipo de actividad económica", example = "P")
        private String type;

        @Schema(description = "Nombre de la empresa", example = "Empresa S.A.")
        private String company;

        @Schema(description = "Cargo en la empresa", example = "Gerente")
        private String position;

        @Schema(description = "Nivel de ingresos", example = "1000")
        private String incomeLevel;

        @Schema(description = "Actividad económica", example = "12345")
        private Integer economicActivity;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData {
        @Schema(description = "Nombre completo", example = "Juan Perez")
        private String completeName;

        @Schema(description = "Número de celular", example = "987654321")
        private String cellPhoneNumber;

        @Schema(description = "Ultima actualización", example = "2021-01-01")
        private String lastUpdate;

        @Schema(description = "Género", example = "M")
        private String gender;

        @Schema(description= "Calle", example = "Calle Falsa")
        private String street;

        @Schema(description = "Número de puerta", example = "123")
        private String doorNumber;

        @Schema(description="Código de departamento", example = "1")
        private Integer departmentCode;

        @Schema(description="Código de barrio", example = "2")
        private Integer neighborhoodCode;

        @Schema(description="Barrio Zona", example = "Calacoto")
        private String neighborhood;

        @Schema(description="Código de calle", example = "3")
        private Integer streetCode;

        @Schema(description="Código de ciudad", example = "4")
        private Integer cityCode;

        @Schema(description="Referencia del domicilio", example = "Casa de Juan")
        private String homeReference;

        @Schema(description="Descripción del apartamento", example = "Apartamento 1")
        private String apartmentDescription;

        @Schema(description="Teléfonos", example = "123456789")
        private String phones;

        @Schema(description="Empleado del banco", example = "3")
        private String bankEmployee;

        @Schema(description = "Correo electrónico", example = "ejemplo@dominio.com")
        private String email;

        @Schema(description = "Sección", example = "3")
        private Integer section;

        @Schema(description = "Zona", example = "4")
        private Integer zone;

        @Schema(description = "Distrito", example = "Distrito Central")
        private String dictrict;

        @Schema(description = "Dirección", example = "Calle Falsa 123")
        private String address;

        @Schema(description = "Departamento", example = "Central")
        private String department;

        @Schema(description = "Piso", example = "5")
        private Integer floor;

        @Schema(description = "Coordenadas GPS", example = "40.7128,-74.0060")
        private String gps;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reference {
        @Schema(description = "Nombre de la referencia", example = "María")
        private String name;

        @Schema(description = "Teléfono de la referencia", example = "987654321")
        private String telephone;

        @Schema(description = "Parentesco", example = "3")
        private Integer relationship;

        @Schema(description = "Tipo de referencia", example = "P")
        private String referenceType;

        @Schema(description = "Tipo de persona", example = "F")
        private String personType;

        @Schema(description = "Ordinal", example = "1")
        private Integer ordinal;
    }
}
