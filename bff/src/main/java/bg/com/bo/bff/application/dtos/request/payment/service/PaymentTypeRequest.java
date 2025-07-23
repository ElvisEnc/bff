package bg.com.bo.bff.application.dtos.request.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeRequest {

    @Schema(description = "Codigo de afiliacion de la persona")
    private String affiliateServiceId;

    @Schema(description = "Codigo del servicio")
    private String serviceCode;

}
