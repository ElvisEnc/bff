package bg.com.bo.bff.application.dtos.response.loans;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPlanResponse {
    @Schema(description = "identificador")
    private Long identifier;

    @Schema(description = "id del prestamo")
    private Long loanId;

    @Schema(description = "número del prestamo")
    private Long loanNumber;

    @Schema(description = "número de cuota")
    private Long quotaNumber;

    @Schema(description = "fecha inicio")
    private String dateInit;

    @Schema(description = "fecha fin")
    private String dateDue;

    @Schema(description = "tipo de cuota")
    private String quotaType;

    @Schema(description = "capital")
    private BigDecimal capital;

    @Schema(description = "interes")
    private BigDecimal interest;

    @Schema(description = "cargo 1")
    private BigDecimal charge1;

    @Schema(description = "cargo 2")
    private BigDecimal charge2;

    @Schema(description = "cargo 3")
    private BigDecimal charge3;

    @Schema(description = "cargo 4")
    private BigDecimal charge4;

    @Schema(description = "cargo 5")
    private BigDecimal charge5;

    @Schema(description = "cargo 6")
    private BigDecimal charge6;

    @Schema(description = "residual")
    private BigDecimal residual;

    @Schema(description = "fecha de registro")
    private String dateRegister;

    @Schema(description = "código de cliente")
    private Long clientCode;

    @Schema(description = "nombre de cliente")
    private String clientName;

    @Schema(description = "producto")
    private String product;

    @Schema(description = "nombre de sucursal")
    private String branchName;

    @Schema(description = "moneda")
    private String currency;

    @Schema(description = "fecha de desembolso")
    private String disbursementDate;

    @Schema(description = "monto de desembolso")
    private BigDecimal disbursementAmount;

    @Schema(description = "periodo")
    private Long period;

    @Schema(description = "teac")
    private BigDecimal teac;

    @Schema(description = "tiempo limite")
    private Long timeLimit;

    @Schema(description = "tipo de tasa")
    private String rateType;

    @Schema(description = "tasa nominal")
    private BigDecimal nominalRate;

    @Schema(description = "tarifa base")
    private BigDecimal baseRate;

    @Schema(description = "nombre de tipo de tasa")
    private String nameTypeRate;

    @Schema(description = "fecha tasa de revisión")
    private String dateReviewRate;

    @Schema(description = "base de revisión de tasa")
    private BigDecimal baseRateReviewPoint;

    @Schema(description = "tipo de pago de interes")
    private String paymentTypeInterest;

    @Schema(description = "cantidad cuotas")
    private Long quantityDues;
}
