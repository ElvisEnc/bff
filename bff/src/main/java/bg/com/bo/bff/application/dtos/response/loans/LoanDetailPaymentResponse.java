package bg.com.bo.bff.application.dtos.response.loans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDetailPaymentResponse {

    @Schema(description = "ID correlativo")
    private long correlativeId;

    @Schema(description = "Número de operación")
    private long nroOperation;

    @Schema(description = "Fecha de alta")
    private String highDate;

    @Schema(description = "Total de cuotas")
    private int totalFee;

    @Schema(description = "Cuota pagada")
    private int feePaid;

    @Schema(description = "Fecha de vencimiento próxima")
    private String expirationNextDate;

    @Schema(description = "Fecha de vencimiento del préstamo")
    private String expirationLoanDate;

    @Schema(description = "Tasa de interés")
    private double interestRate;

    @Schema(description = "Fecha de valor")
    private String dateValue;

    @Schema(description = "Saldo actual")
    private double currentBalance;

    @Schema(description = "Estado")
    private String status;

    @Schema(description = "Saldo de seguro")
    private double balanceSecure;

    @Schema(description = "Cargos devengados")
    private double accruedCharges;

    @Schema(description = "Intereses corrientes")
    private double interestCurrent;

    @Schema(description = "Intereses penales")
    private double penaltyInterest;

    @Schema(description = "Capital")
    private double capital;

    @Schema(description = "Formulario")
    private int form;

    @Schema(description = "Monto")
    private double amount;

    @Schema(description = "Moneda del seguro")
    private int secureCurrency;

    @Schema(description = "Monto seguro obligatorio")
    private double amountSecureMandatory;

    @Schema(description = "Préstamo pagado")
    private boolean paid;
}
