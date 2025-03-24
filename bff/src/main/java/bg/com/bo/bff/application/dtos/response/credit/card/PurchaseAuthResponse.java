package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseAuthResponse {
    @Schema(description = "fecha de proceso")
    private String processDate;

    @Schema(description = "tipo")
    private String type;

    @Schema(description = "monto")
    private String amount;

    @Schema(description = "código de moneda")
    private String currency;

    @Schema(description = "estado")
    private String status;

    @Schema(description = "origen")
    private String origin;

    @Schema(description = "Fecha de inicio")
    private String initDate;

    @Schema(description = "Fecha de fin")
    private String endDate;
}
