package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebtDetail {

    @Schema(description = "Permite modificacion")
    private String invoiceCanModifyData;

    @Schema(description = "Nombre para la factura")
    private String invoiceName;

    @Schema(description = "Número de NIT/CI para la factura")
    private String invoiceNITCI;

    @Schema(description = "Complemento de la factura")
    private String invoiceComplementId;

    @Schema(description = "e-mail para la factura")
    private String invoiceEmail;

    @Schema(description = "Tipo de factura")
    private String invoiceType;

    @Schema(description = "descripción")
    private String description;

    @Schema(description = "código de referencia, equivale al internalCode")
    private String referenceCode;

    @Schema(description = "Mes")
    private Integer monthPeriod;

    @Schema(description = "Año")
    private Integer yearPeriod;

    @Schema(description = "Monto de la comisión")
    private BigDecimal commissionAmount;

    @Schema(description = "Código de la moneda")
    private String currencyCode;

    @Schema(description = "Monto")
    private BigDecimal amount;

    @Schema(description = "Monto acumulado")
    private BigDecimal accumulatedAmount;

    @Schema(description = "Identificador")
    private Long identifier;

    @Schema(description = "Tipo de validación")
    private String validationType;

    @Schema(description = "Detalles")
    private String detail;

    @Schema(description = "Detalles adicionales")
    private String additionalDataDetails;

    @Schema(description = "Plan de pago")
    private String paymentPlan;

    @Schema(description = "UUID")
    private String idGenerated;

    private String concept;

}
