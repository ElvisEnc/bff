package bg.com.bo.bff.application.dtos.response.credit.card;

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
public class CreditCardStatementsResponse {
    @Schema(description = "número de la tarjeta")
    private String cardNumber;

    @Schema(description = "descripción")
    private String description;

    @Schema(description = "código de la moneda")
    private String currency;

    @Schema(description = "monto en moneda extranjera")
    private BigDecimal mrAmount;

    @Schema(description = "monto en moneda local")
    private BigDecimal mlAmount;

    @Schema(description = "fecha de transacción")
    private String transactionDate;

    @Schema(description = "fecha de proceso")
    private String processDate;

    @Schema(description = "nombre de cliente")
    private String clientName;

    @Schema(description = "tipo de transacción")
    private String transactionType;

    @Schema(description = "descripción del tipo de transacción")
    private String transactionTypeDesc;

    @Schema(description = "estado de transacción")
    private String transactionStatus;

    @Schema(description = "número de secuencia")
    private BigDecimal sequenceNumber;

    @Schema(description = "número de pago realizado")
    private String feeNumber;

    @Schema(description = "fecha de parametros")
    private String paramDate;
}
