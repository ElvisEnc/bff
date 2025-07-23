package bg.com.bo.bff.application.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateVoucherResponse {

    @Schema(description = "Número de la transacción")
    private Integer transactionNumber;

    @Schema(description = "Tipo de transacción realizada")
    private Integer transactionType;

    @Schema(description = "Monto total de la transacción")
    private Double amount;

    @Schema(description = "Código de la moneda utilizada")
    private Integer currency;

    @Schema(description = "Moneda equivalente del monto acreditado")
    private Integer currencyCredit;

    @Schema(description = "Moneda equivalente del monto debitado")
    private Integer currencyDebit;

    @Schema(description = "Nombre del remitente")
    private String senderName;

    @Schema(description = "Número de cuenta del remitente")
    private String senderAccountNumber;

    @Schema(description = "JTS del remitente")
    private Integer senderJts;

    @Schema(description = "Nombre del destinatario")
    private String receiverName;

    @Schema(description = "JTS del destinatario")
    private Integer receiverJts;

    @Schema(description = "Número de cuenta del destinatario")
    private String receiverAccountNumber;

    @Schema(description = "Banco del destinatario")
    private String receiverBank;

    @Schema(description = "Monto debitado en moneda equivalente")
    private Double debitAmount;

    @Schema(description = "Tipo de cambio usado para el débito")
    private Double debitExchangeRate;

    @Schema(description = "Monto acreditado en moneda equivalente")
    private Double creditAmount;

    @Schema(description = "Tipo de cambio usado para el crédito")
    private Double creditExchangeRate;

    @Schema(description = "Descripción o concepto de la transacción")
    private String description;
}
