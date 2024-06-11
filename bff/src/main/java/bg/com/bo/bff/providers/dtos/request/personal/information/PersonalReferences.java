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
    @JsonProperty("TELEFONOS")
    private String phones;
    @JsonProperty("TIPOREFERENCIA")
    private String referenceType;
    @JsonProperty("ORDINAL")
    private String ordinal;
    @JsonProperty("TIPO_PERSONA")
    private String persontype;
    @JsonProperty("NOMBRE")
    private String name;
    @JsonProperty("RELACION")
    private String relationship;

}
