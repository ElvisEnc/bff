package bg.com.bo.bff.providers.dtos.request.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateDataPerson {
    @JsonProperty("COORDENADAS")
    private String coordinates;

    @JsonProperty("ZONA")
    private String zone;

    @JsonProperty("NOMBRECOMPLETO")
    private String fullName;

    @JsonProperty("OFICINA")
    private String office;

    @JsonProperty("COD_DEPARTAMENTO")
    private String departmentCode;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("APELLIDOESPOSO")
    private String husbandLastName;

    @JsonProperty("BARRIOZONA")
    private String neighborhood;

    @JsonProperty("FECHAACTUALIZACION")
    private String updateDate;

    @JsonProperty("DEPARTAMENTO")
    private String department;

    @JsonProperty("CIUDAD")
    private String city;

    @JsonProperty("COD_CIUDAD")
    private String cityCode;

    @JsonProperty("NOMBRE_CONYUGUE")
    private String spouseName;

    @JsonProperty("EMPLEADO_BANCO")
    private String bankEmployee;

    @JsonProperty("COD_BARRIO")
    private String neighborhoodCode;

    @JsonProperty("APARTAMENTO")
    private String apartment;

    @JsonProperty("REFERENCIADOMICILIO")
    private String homeReference;

    @JsonProperty("TELEFONOS")
    private String phones;

    @JsonProperty("FECHAULTACTUALIZACION")
    private String lastUpdateDate;

    @JsonProperty("CELULAR")
    private String cellphone;

    @JsonProperty("USA_APELLIDOESPOSO")
    private String usesSpouseLastName;

    @JsonProperty("CALLE")
    private String street;

    @JsonProperty("NUMEROPUERTA")
    private String doorNumber;

    @JsonProperty("ACTIVIDAD_ECONOMICA")
    private String economicActivity;

    @JsonProperty("SEXO")
    private String gender;

    @JsonProperty("ESTADOCIVIL")
    private String maritalStatus;

    @JsonProperty("PISO")
    private String floor;

    @JsonProperty("COD_CALLE")
    private String streetCode;

    @JsonProperty("NIVEL_INGRESOS")
    private String incomeLevel;

    @JsonProperty("FUENTE_INGRESO")
    private String incomeSource;

    @JsonProperty("EMPRESA")
    private String company;

    @JsonProperty("CARGO")
    private String position;

    @JsonProperty("RELACION")
    private String relationship;

    @JsonProperty("NOMBREREFERENCIA")
    private String referenceName;

    @JsonProperty("TELEFONOREFERENCIA")
    private String referencePhone;

    @JsonProperty("ORDINAL")
    private String ordinal;

}

