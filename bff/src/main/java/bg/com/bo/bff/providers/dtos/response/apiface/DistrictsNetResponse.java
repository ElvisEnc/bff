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
public class DistrictsNetResponse {
    @JsonProperty("CodigoError")
    private String errorCode;
    @JsonProperty("Datos")
    private DistrictData result;
    @JsonProperty("Mensaje")
    private String message;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistrictData {
        @JsonProperty("cur_localidades")
        private List<DistrictNetDetail> data;
    }
}
