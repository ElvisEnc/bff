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
public class DistrictsNetRequest {
    @JsonProperty("codDepartamento")
    private String codDepartamento;
}
