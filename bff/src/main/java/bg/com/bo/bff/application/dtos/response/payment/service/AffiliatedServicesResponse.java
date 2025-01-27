package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedServicesResponse {

    @Schema(description = "Código de servicio")
    private String serviceCode;

    @Schema(description = "nombre del servicio")
    private String serviceName;

    @Schema(description = "Lista de servicios afiliados")
    private List<Service> data;

    @Schema(description = "Registro total de afiliados por servicio")
    private int total;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Service {

        @Schema(description = "Código de servicio")
        private String serviceCode;

        @Schema(description = "nombre del servicio")
        private String serviceName;

        @Schema(description = "id de la afiliación")
        private String affiliateServiceId;

        @Schema(description = "código o número de la afiliación")
        private String internalCode;

        @Schema(description = "referencia de la afiliación")
        private String referenceName;

        @Schema(description = "nombre del titular del servicio")
        private String nameHolder;

        @Schema(description = "gestion")
        private String year;

        @Schema(description = "estado de contingencia")
        private Boolean contingency;
    }
}
