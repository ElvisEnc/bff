package bg.com.bo.bff.providers.dtos.responses.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientData {

    @JsonProperty("NUMEROPERSONAFISICA")
    private int personNumber;

    @JsonProperty("FECHAULTACTUALIZACION")
    private String lastUpdateDate;

    @JsonProperty("NOMBRECOMPLETO")
    private String fullName;

    @JsonProperty("ESTADOCIVIL")
    private String maritalStatus;

    @JsonProperty("SEXO")
    private String gender;

    @JsonProperty("CALLE")
    private String street;

    @JsonProperty("NUMEROPUERTA")
    private String doorNumber;

    @JsonProperty("PISO")
    private int floor;

    @JsonProperty("CIUDAD")
    private String city;

    @JsonProperty("DEPARTAMENTO")
    private String department;

    @JsonProperty("COD_DEPARTAMENTO")
    private int departmentCode;

    @JsonProperty("BARRIOZONA")
    private String neighborhood;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("CELULAR")
    private String mobile;

    @JsonProperty("COD_BARRIO")
    private int neighborhoodCode;

    @JsonProperty("COD_CALLE")
    private int streetCode;

    @JsonProperty("COD_CIUDAD")
    private int cityCode;

    @JsonProperty("APELLIDOESPOSO")
    private String spouseLastName;

    @JsonProperty("USA_APELLIDOESPOSO")
    private String usesSpouseLastName;

    @JsonProperty("REFERENCIADOMICILIO")
    private String homeReference;

    @JsonProperty("OFICINA")
    private String office;

    @JsonProperty("ZONA")
    private int zone;

    @JsonProperty("NOMBRE_CONYUGUE")
    private String spouseName;

    @JsonProperty("APARTAMENTO")
    private String apartment;

    @JsonProperty("TELEFONOS")
    private String phones;

    @JsonProperty("FECHAACTUALIZACION")
    private String updateDate;

    @JsonProperty("NIVEL_INGRESOS")
    private String incomeLevel;

    @JsonProperty("ACTIVIDAD_ECONOMICA")
    private int economicActivity;

    @JsonProperty("EMPLEADO_BANCO")
    private String bankEmployee;

    @JsonProperty("COORDENADAS")
    private String coordinates;
}
