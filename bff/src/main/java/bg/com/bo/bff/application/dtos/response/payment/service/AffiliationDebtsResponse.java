package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationDebtsResponse {
    @Schema(description = "Código de afiliación")
    private String affiliateServiceId;

    @Schema(description = "Código de servicio")
    private String serviceCode;

    private List<DebtDetail> debtDetails;
}
