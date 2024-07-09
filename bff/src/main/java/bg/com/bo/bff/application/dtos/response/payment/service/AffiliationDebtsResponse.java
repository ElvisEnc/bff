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

    @Schema(description = "Nit para la factura")
    private String invoiceNit;

    @Schema(description = "Nombre para la factura")
    private String invoiceName;

    @Schema(description = "Booleano si puede modificar los datos de la factura")
    private Boolean invoiceCanModify;

    private List<DebtDetail> debtDetails;
}
