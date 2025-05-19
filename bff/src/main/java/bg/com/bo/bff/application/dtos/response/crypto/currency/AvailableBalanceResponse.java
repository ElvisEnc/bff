package bg.com.bo.bff.application.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableBalanceResponse {

    @Schema(description = "Moneda de la cuenta")
    private String currency;

    @Schema(description = "Saldo disponible en la cuenta")
    private Double availableBalance;

    @Schema(description = "Número de cuenta")
    private Integer account;

    @Schema(description = "Estado actual de la cuenta")
    private String status;

    @Schema(description = "Identificador JTS de la cuenta")
    private Integer jtsOid;

    @Schema(description = "Tipo de producto asociado a la cuenta")
    private String product;

}
