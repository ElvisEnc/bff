package bg.com.bo.bff.providers.dtos.request.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateDataPerson {
    @JsonProperty("COORDENADAS")
    private String coordinates;

    @JsonProperty("ZONA")
    private int zone;

    @JsonProperty("TELEFONOS")
    private String phones;

    @JsonProperty("COD_DEPARTAMENTO")
    private int departmentCode;

    @JsonProperty("EMAIL")
    private String email;

    @JsonProperty("APELLIDOESPOSO")
    private String husbandLastName;

    @JsonProperty("BARRIOZONA")
    private String neighborhood;

    @JsonProperty("DEPARTAMENTO")
    private String department;

    @JsonProperty("CIUDAD")
    private String city;

    @JsonProperty("COD_CIUDAD")
    private int cityCode;

    @JsonProperty("NOMBRE_CONYUGUE")
    private String spouseName;

    @JsonProperty("EMPLEADO_BANCO")
    private String bankEmployee;

    @JsonProperty("COD_BARRIO")
    private int neighborhoodCode;

    @JsonProperty("APARTAMENTO")
    private String apartment;

    @JsonProperty("REFERENCIADOMICILIO")
    private String homeReference;

    @JsonProperty("CELULAR")
    private String mobile;

    @JsonProperty("USA_APELLIDOESPOSO")
    private String usesSpouseLastName;

    @JsonProperty("CALLE")
    private String street;

    @JsonProperty("NUMEROPUERTA")
    private String doorNumber;

    @JsonProperty("ACTIVIDAD_ECONOMICA")
    private int economicActivity;

    @JsonProperty("ESTADOCIVIL")
    private String maritalStatus;

    @JsonProperty("PISO")
    private String floor;

    @JsonProperty("COD_CALLE")
    private int streetCode;

    @JsonProperty("NIVEL_INGRESOS")
    private int incomeLevel;

    @JsonProperty("FUENTE_INGRESO")
    private String incomeSource;

    @JsonProperty("EMPRESA")
    private String company;

    @JsonProperty("CARGO")
    private String position;

    @JsonProperty("RELACION")
    private int relationship;

    @JsonProperty("NOMBREREFERENCIA")
    private String referenceName;

    @JsonProperty("TELEFONOREFERENCIA")
    private String referencePhone;

    @JsonProperty("ORDINAL")
    private int ordinal;

}

