package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ListServicesResponse {
    private List<Service> data;

    @AllArgsConstructor
    @Builder
    @Data
    public static class Service{
        @Schema(example = "1", description = "Código del servicio")
        private String serviceCode;
        @Schema(example = "Entel", description = "Nombre del servicio")
        private String serviceName;
    }
}
