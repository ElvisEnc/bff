package bg.com.bo.bff.providers.dtos.response.loans.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListLoansMWResponse {
    private List<LoanDataMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanDataMW {
        @Schema(description = "ID del préstamo")
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
        private String amountDisbursement;

        @Schema(description = "Balance")
        @JsonProperty("balance")
        private String balance;

        @Schema(description = "Moneda")
        @JsonProperty("currency")
        private String currency;

        @Schema(description = "Descripción de la moneda")
        @JsonProperty("currencyDescription")
        private String currencyDescription;

        @Schema(description = "Fecha de vencimiento")
        @JsonProperty("expirationDate")
        private String expirationDate;

        @Schema(description = "Tasa")
        @JsonProperty("rate")
        private String rate;

        @Schema(description = "Fecha del último pago")
        @JsonProperty("lastPaymentDate")
        private String lastPaymentDate;

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
        private String feeAmountK;

        @Schema(description = "Monto de cuota I")
        @JsonProperty("feeAmountI")
        private String feeAmountI;

        @Schema(description = "Monto de cuota C")
        @JsonProperty("feeAmountC")
        private String feeAmountC;

        @Schema(description = "Pago de cuota")
        @JsonProperty("feePayment")
        private String feePayment;

        @Schema(description = "Fecha de proceso")
        @JsonProperty("processDate")
        private String processDate;
    }
}
