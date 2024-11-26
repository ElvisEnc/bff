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
public class PayCreditCardResponse {
    @Schema(description = "estado")
    private String status;

    @Schema(description = "transacción id")
    private String transactionId;

    @Schema(description = "id maestro")
    private String maeId;

    @Schema(description = "número de asiento")
    private String accountingEntry;

    @Schema(description = "fecha")
    private String accountingDate;

    @Schema(description = "hora")
    private String accountingTime;

    @Schema(description = "monto debitado")
    private BigDecimal amountDebited;

    @Schema(description = "tipo de cambio debito")
    private BigDecimal exchangeRateDebit;

    @Schema(description = "tipo de cambio credito")
    private BigDecimal exchangeRateCredit;

    @Schema(description = "monto")
    private BigDecimal amount;

    @Schema(description = "moneda")
    private String currency;

    @Schema(description = "numero de cuenta origen")
    private String fromAccountNumber;

    @Schema(description = "titular origen")
    private String fromHolder;

    @Schema(description = "descripción")
    private String description;

    @Schema(description = "moneda de origen")
    private String fromCurrency;

    @Schema(description = "moneda destino")
    private String toCurrency;

    @Schema(description = "id de la tarjeta de credito")
    private String tcAccountId;
}
