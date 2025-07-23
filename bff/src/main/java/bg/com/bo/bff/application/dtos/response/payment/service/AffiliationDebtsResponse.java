package bg.com.bo.bff.application.dtos.response.payment.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AffiliationDebtsResponse {
    @Schema(description = "Código de afiliación")
    private String affiliateServiceId;

    @Schema(description = "Código de servicio")
    private String serviceCode;

    private List<DebtDetail> debtDetails;
}
