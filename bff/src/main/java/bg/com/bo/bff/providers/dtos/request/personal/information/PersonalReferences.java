package bg.com.bo.bff.providers.dtos.request.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalReferences {

    @JsonProperty("NOMBRE")
    private String name;

    @JsonProperty("TELEFONOS")
    private String phone;

    @JsonProperty("RELACION")
    private int relation;

    @JsonProperty("TIPOREFERENCIA")
    private String referenceType;

    @JsonProperty("TIPO_PERSONA")
    private String personType;

    @JsonProperty("ORDINAL")
    private int ordinal;

}
