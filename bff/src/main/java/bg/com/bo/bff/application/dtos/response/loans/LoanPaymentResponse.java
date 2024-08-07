package bg.com.bo.bff.application.dtos.response.loans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentResponse {

    @Schema(description = "Estado de la transacción")
    private String status;

    @Schema(description = "Número de transacción")
    private String transactionId;

    @Schema(description = "Identificador MAE")
    private String maeId;

    @Schema(description = "Entrada contable")
    private String accountingEntry;

    @Schema(description = "Fecha contable")
    private String accountingDate;

    @Schema(description = "Hora contable")
    private String accountingTime;

    @Schema(description = "Número de cuenta de origen")
    private String originAccountNumber;

    @Schema(description = "Monto")
    private double amount;

    @Schema(description = "Moneda")
    private String currencyCode;

    @Schema(description = "Titular de la cuenta de origen")
    private String fromHolder;

    @Schema(description = "Moneda de origen")
    private String fromCurrencyCode;

    @Schema(description = "Monto debitado")
    private double amountDebited;

    @Schema(description = "Tipo de cambio de débito")
    private double exchangeRateDebit;

    @Schema(description = "Monto del seguro")
    private double insuranceAmount;

    @Schema(description = "Moneda del seguro")
    private String currencyCodeInsurance;

    @Schema(description = "Monto del seguro debitado")
    private double amountDebitInsurance;

    @Schema(description = "Tipo de cambio del seguro debitado")
    private double exchangeRateDebitInsurance;

    @Schema(description = "Número de préstamo de destino")
    private String loanId;

    @Schema(description = "Capital del préstamo")
    private double loanCapital;

    @Schema(description = "Interés corriente")
    private double currentInterest;

    @Schema(description = "Interés de penalización")
    private double penaltyInterest;

    @Schema(description = "Cargos acumulados")
    private double accruedCharges;

    @Schema(description = "Monto de formularios")
    private double formsAmount;

    @Schema(description = "Próxima fecha de vencimiento")
    private String nextDueDate;

    @Schema(description = "Total de cuotas")
    private double totalInstallments;

    @Schema(description = "Cuotas pagadas")
    private double paidInstallments;
}
