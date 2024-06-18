package bg.com.bo.bff.application.dtos.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
        @Schema(description = "Estado civil", example = "S", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "El  estado civil no debe estar vacio")
        @Pattern(regexp = "^[SCUVD]$", message = "El campo estado civil solo puede tener los valores S,C,U,V,D")
        private String status;

        @Schema(description = "Apellido del cónyuge", example = "Pérez")
        private String spouseLastName;

        @Schema(description = "Nombre del cónyuge", example = "Juan")
        private String spouseName;

        @Schema(description = "Usa el nombre del esposo", example = "S o N", requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(regexp = "^[SN]$", message = "El campo usa apellido del esposo solo puede tener los valores S or N,")
        @NotBlank(message = "El campo usesSpouseLastName solo puede tener los valores S o N")
        private String usesSpouseLastName;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EconomicalActivity {
        @Schema(description = "Tipo de actividad económica", example = "65191")
        private String economicActivity;

        @Schema(description = "Nombre de la empresa", example = "Empresa S.A.")
        private String company;

        @Schema(description = "Cargo en la empresa", example = "Gerente")
        private String position;

        @Schema(description = "Nivel de ingresos", example = "Menos de $ 600")
        private String incomeLevel;

        @Schema(description = "Rubro actividad economica", example = " Industria manufacturera")
        private String category;

        @Schema(description = "Tipo de actividad económica", example = "P")
        private String type;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonalData {


        @Schema(description = "Número de teléfono", example = "123456789")
        private String telephoneNumber;

        @Schema(description = "Número de celular", example = "70003030")
        @NotBlank(message = "El número celular de  no puede estar vacio")
        private String cellphone;


        @Schema(description = "Correo electrónico", example = "ejemplo@dominio.com")
        @NotBlank(message = "El correo electronico  no puede estar vacio")
        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Formato de correo incorrecto")
        private String email;

        @Schema(description = "Codigo de ciudad", example = "3")
        private String section;

        @NotBlank(message = "El codigo de zon no puede estar vacio")
        @Schema(description = "Zona", example = "4")
        private String zone;

        @Schema(description = "Ciudad", example = "Distrito Central")
        private String dictrict;

        @Schema(description = "Dirección", example = "Calle Falsa 123")
        private String address;

        @Schema(description = "Departamento", example = "Central")
        private String department;

        @Schema(description = "Codigo Departamento", example = "Central")
        @NotBlank(message = "El codigo de departamento no puede estar vacio")
        private String departmentCode;



        @Schema(description = "Piso", example = "5")
        private String floor;

        @Schema(description = "Piso", example = "5")
        private String apartment;


        @Schema(description = "Coordenadas GPS", example = "40.7128,-74.0060")
        private String gps;

        @Schema(description = "Referencia de domicilio", example = "A una cuadra del teleferico")
        private String homeReference;

        @Schema(description = "Número de domicilio", example = "12121")
        private String doorNumber;

        @Schema(description = "Es empleado Banco", example = "0")
        private String bankEmployee;


        @Schema(description = "Codigo de Barrio", example = "0")
        private String neighborhoodCode;


    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Reference {
        @NotBlank(message = "El nombre de la referencia no puede estar vacio")
        @Schema(description = "Nombre de la referencia", example = "María")
        private String name;
        @NotBlank(message = "El teléfono de la referencia no puede estar vacio")
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
