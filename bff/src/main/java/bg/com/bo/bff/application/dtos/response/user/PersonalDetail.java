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
        private String spouseLastName;

        @Schema(description = "Nombre del cónyuge", example = "Juan")
        private String spouseName;
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
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData {
        @Schema(description = "Nombre completo", example = "Juan Perez")
        private String completeName;

        @Schema(description = "Número de teléfono", example = "123456789")
        private String telephoneNumber;

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
        private String telephone;
    }
}
