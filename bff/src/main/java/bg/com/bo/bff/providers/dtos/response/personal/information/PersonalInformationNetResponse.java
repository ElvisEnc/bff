package bg.com.bo.bff.providers.dtos.response.personal.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "PersonalInformationNetResponse")
public class PersonalInformationNetResponse {

    @JsonProperty("CodigoError")
    private String errorCode;

    @JsonProperty("Datos")
    private DataContent dataContent;

    @JsonProperty("Mensaje")
    private String message;

}
