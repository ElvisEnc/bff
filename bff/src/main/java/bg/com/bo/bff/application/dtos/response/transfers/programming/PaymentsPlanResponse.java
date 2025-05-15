package bg.com.bo.bff.application.dtos.response.transfers.programming;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentsPlanResponse {

    @Schema(description = "Identificador del pago")
    private String paymentId;

    @Schema(description = "Identificador de la transferencia")
    private String progTransferId;

    @Schema(description = "Trackeo del proceso")
    private String trackingProcess;

    @Schema(description = "Fecha de registro")
    private String registerDate;

    @Schema(description = "Monto para la transferencia")
    private String amount;

    @Schema(description = "Codigo de moneda de la transferencia")
    private String currency;

    @Schema(description = "Estado de la transferencia")
    private String status;

    @Schema(description = "Codigo de error")
    private String processError;

    @Schema(description = "Nro de comprobante")
    private String voucher;

    @Schema(description = "Nro de asiento contable")
    private String accountingEntry;

    @Schema(description = "Fecha de asiento contable")
    private String accountEntryDate;

    @Schema(description = "Sucursal")
    private String branch;

    @Schema(description = "Codigo TZ_LOCK")
    private String tzLock;

    @Schema(description = "Codigo de orden")
    private String orderCode;

    @Schema(description = "Numero de lote")
    private String batchNumber;

    @Schema(description = "Numero de cuota")
    private String ordinal;

}
