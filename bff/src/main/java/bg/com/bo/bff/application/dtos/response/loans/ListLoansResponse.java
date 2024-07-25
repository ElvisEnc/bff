package bg.com.bo.bff.application.dtos.response.loans;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListLoansResponse {
    @Schema(description = "Id del préstamo")
    @JsonProperty("loanId")
    private String loanId;

    @Schema(description = "Número del préstamo")
    @JsonProperty("loanNumber")
    private String loanNumber;

    @Schema(description = "Nombre del cliente")
    @JsonProperty("customerName")
    private String customerName;

    @Schema(description = "Fecha de desembolso")
    @JsonProperty("disbursementDate")
    private String disbursementDate;

    @Schema(description = "Monto del desembolso")
    @JsonProperty("amountDisbursement")
    private Double amountDisbursement;

    @Schema(description = "Balance")
    @JsonProperty("balance")
    private Double balance;

    @Schema(description = "Moneda")
    @JsonProperty("currency")
    private String currency;

    @Schema(description = "Fecha de vencimiento")
    @JsonProperty("expirationDate")
    private String expirationDate;

    @Schema(description = "Tasa")
    @JsonProperty("rate")
    private Double rate;

    @Schema(description = "Producto")
    @JsonProperty("product")
    private String product;

    @Schema(description = "Código de estado")
    @JsonProperty("stateCode")
    private String stateCode;

    @Schema(description = "Estado")
    @JsonProperty("state")
    private String state;

    @Schema(description = "Fecha de pago de la cuota")
    @JsonProperty("feePaymentDate")
    private String feePaymentDate;

    @Schema(description = "Fecha de vencimiento de la cuota")
    @JsonProperty("feePaymentDueDate")
    private String feePaymentDueDate;

    @Schema(description = "Monto de cuota K")
    @JsonProperty("feeAmountK")
    private Double feeAmountK;

    @Schema(description = "Monto de cuota I")
    @JsonProperty("feeAmountI")
    private Double feeAmountI;

    @Schema(description = "Monto de cuota C")
    @JsonProperty("feeAmountC")
    private Double feeAmountC;

    @Schema(description = "Pago de cuota")
    @JsonProperty("feePayment")
    private Double feePayment;

    @Schema(description = "Fecha de proceso")
    @JsonProperty("processDate")
    private String processDate;
}
