package bg.com.bo.bff.providers.dtos.response.apiface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentsNetResponse {

    @JsonProperty("endPointACListadoPlazaResult")
    private List<DepartmentNetDetail> data;
}
