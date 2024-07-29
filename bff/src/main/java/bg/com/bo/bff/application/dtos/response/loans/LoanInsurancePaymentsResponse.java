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
public class LoanInsurancePaymentsResponse {
    @Schema(description = "Número de garantía")
    private Long warrantyNumber;

    @Schema(description = "Descripción")
    private String description;

    @Schema(description = "Código de moneda")
    private String currencyCode;

    @Schema(description = "Descripción de la moneda")
    private String currencyDescription;

    @Schema(description = "Número de pago")
    private Integer paymentNumber;

    @Schema(description = "Fecha de pago")
    private String paymentDate;

    @Schema(description = "Monto de pago")
    private BigDecimal amount;

    @Schema(description = "Indice de pago")
    private Integer index;
}
