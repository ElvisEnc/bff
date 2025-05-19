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
public class ExchangeOperationResponse {

    @Schema(description = "Importe debitado en la operación de cambio")
    private Double importDebited;

    @Schema(description = "Código de la moneda utilizada en la operación")
    private int currency;

    @Schema(description = "Número de asiento contable")
    private Long seatNo;

    @Schema(description = "ID del comprobante generado")
    private int receiptId;

    @Schema(description = "Tipo de cambio utilizado para el crédito")
    private int tcCredit;

    @Schema(description = "Tipo de cambio utilizado para el débito")
    private Double tcDebit;

    @Schema(description = "Código de la sucursal que realizó la operación")
    private int branch;

    @Schema(description = "Fecha del asiento contable")
    private String dateSeat;

    @Schema(description = "Importe del ITF")
    private Double importItf;

    @Schema(description = "Importe acreditado en la cuenta")
    private Double importAccredited;
}
