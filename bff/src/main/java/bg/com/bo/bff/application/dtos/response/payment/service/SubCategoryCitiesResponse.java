package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class SubCategoryCitiesResponse {

    private List<City> data;

    @AllArgsConstructor
    @Data
    public static class City{
        @Schema(example = "1", description = "ID de ciduad.")
        private Integer cityId;
        @Schema(example = "Oruro", description = "Nombre de la ciudad.")
        private String cityName;
    }
}
