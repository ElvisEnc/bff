package bg.com.bo.bff.providers.dtos.response.qr;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptDetailQrPayment {

    @Schema(description = "accountingEntry número de cuenta de entrada", example = "1234567890")
    private String accountingEntry;
    @Schema(description = "accountingDate fecha de transacción", example = "17/05/2024")
    private String accountingDate;
    @Schema(description = "accountingTime hora de transacción", example = "18:02:00")
    private String accountingTime;
    @Schema(description = "amountDebited Monto debitado", example = "20.00")
    private String amountDebited;
    @Schema(description = "amountCredited Monto Acreditado", example = "20.00")
    private String amountCredited;
    @Schema(description = "exchangeRateDebit Cantidad de transacciones debitadas", example = "1")
    private String exchangeRateDebit;
    @Schema(description = "exchangeRateCredit Cantidad de transacciones acreditadas", example = "1")
    private String exchangeRateCredit;
    @Schema(description = "amount Monto de transacción", example = "20.00")
    private String amount;
    @Schema(description = "currency moneda", example = "BOB")
    private String currency;
    @Schema(description = "fromAccountNumber Número de cuenta origen", example = "1234567890")
    private String fromAccountNumber;
    @Schema(description = "fromHolder Nombre de persona origen", example = "Juan Mamani")
    private String fromHolder;
    @Schema(description = "toAccountNumber  Número de cuenta destino", example = "1234567890")
    private String toAccountNumber;
    @Schema(description = "toHolder Nombre de persona destiono", example = "Marcos")
    private String toHolder;
    @Schema(description = "description Glosa", example = "descripción")
    private String description;
    @Schema(description = "fromCurrency  codigo de moneda origen", example = "023")
    private String fromCurrency;
    @Schema(description = "toCurrency codigo de moneda destino", example = "034")
    private String toCurrency;

}
