package bg.com.bo.bff.application.dtos.response.loans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentsResponse {
    @Schema(description = "Fecha")
    private String date;

    @Schema(description = "Número de asiento")
    private String accountEntry;

    @Schema(description = "Capital adelantado")
    private BigDecimal advancedCapital;

    @Schema(description = "Capital original")
    private BigDecimal originalCapital;

    @Schema(description = "Capital pagado")
    private BigDecimal capitalPaid;

    @Schema(description = "Gastos")
    private BigDecimal expenses;

    @Schema(description = "Interes pagado")
    private BigDecimal interestAmountPaid;

    @Schema(description = "Mora pagada")
    private BigDecimal payLateFees;

    @Schema(description = "Saldo")
    private BigDecimal balance;

    @Schema(description = "Tipo de movimiento")
    private String typeMovement;

    @Schema(description = "Total cuota")
    private BigDecimal totalInstallment;

    @Schema(description = "Sucursal de movimiento")
    private String branch;
}
