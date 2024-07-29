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
public class LoanPaymentsResponse {
    @Schema(description = "Fecha")
    private String date;

    @Schema(description = "Número de asiento")
    private String accountEntry;

    @Schema(description = "Capital adelantado")
    private String advancedCapital;

    @Schema(description = "Capital original")
    private String originalCapital;

    @Schema(description = "Capital pagado")
    private String capitalPaid;

    @Schema(description = "Gastos")
    private String expenses;

    @Schema(description = "Interes pagado")
    private String interestAmountPaid;

    @Schema(description = "Mora pagada")
    private String payLateFees;

    @Schema(description = "Saldo")
    private String balance;

    @Schema(description = "Tipo de movimiento")
    private String typeMovement;

    @Schema(description = "Total cuota")
    private String totalInstallment;

    @Schema(description = "Sucursal de movimiento")
    private String branch;
}
