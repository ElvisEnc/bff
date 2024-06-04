package bg.com.bo.bff.providers.dtos.response.apiface;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistrictNetDetail {
    @JsonProperty("COD_LOCALIDAD")
    private String codeDistrict;

    @JsonProperty("DESC_CIUDAD")
    private String description;
}
