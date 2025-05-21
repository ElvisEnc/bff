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
public class AccountExtractResponse {

    @Schema(description = "Indica si existe un comprobante para la transacción")
    private boolean existsVoucher;

    @Schema(description = "Fecha de la transacción en formato yyyy-MM-dd")
    private String transactionDate;

    @Schema(description = "Hora de la transacción en formato HH:mm:ss")
    private String transactionTime;

    @Schema(description = "Monto de la transacción")
    private String amount;

    @Schema(description = "Descripción o detalle de la transacción")
    private String description;

    @Schema(description = "Día de la transacción")
    private int day;

    @Schema(description = "Mes de la transacción")
    private String month;

    @Schema(description = "Año de la transacción")
    private String year;

    @Schema(description = "Tipo de transacción")
    private String transactionType;

    @Schema(description = "Fecha en la que se procesó la transacción")
    private String processDate;

    @Schema(description = "Sucursal donde se realizó la transacción")
    private String branch;

    @Schema(description = "Número de asiento contable asociado")
    private String seatNumber;

    @Schema(description = "Número correlativo de la transacción")
    private String correlative;

    @Schema(description = "Saldo actual de la cuenta después de la transacción")
    private String currentBalance;

    @Schema(description = "Símbolo de la moneda")
    private String currencySymbol;
}
