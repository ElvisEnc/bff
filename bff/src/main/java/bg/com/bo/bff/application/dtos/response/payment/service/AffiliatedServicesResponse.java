package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedServicesResponse {
    @Schema(description = "id de la afiliación")
    private String affiliateServiceId;

    @Schema(description = "Código de servicio")
    private String serviceCode;

    @Schema(description = "nombre del servicio")
    private String serviceName;

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
