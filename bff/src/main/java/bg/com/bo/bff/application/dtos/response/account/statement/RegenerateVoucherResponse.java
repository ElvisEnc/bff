package bg.com.bo.bff.application.dtos.response.account.statement;


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
public class RegenerateVoucherResponse {

    @Schema(description = "Identificador del QR")
    private String qrId;

    @Schema(description = "Numero de Identificacion")
    private String identificationNumber;

    @Schema(description = "Nombre del negocio")
    private String businessName;

    @Schema(description = "Fecha de expiracion")
    private String expirationDate;

    @Schema(description = "campo libre")
    private String freeField;

    @Schema(description = "Indica si sera de uso unico")
    private Integer singleUse;

    @Schema(description = "moneda del qr")
    private String qrCurrency;

    @Schema(description = "numero de serie")
    private String serialNumber;

    @Schema(description = "numero de cuenta pagable")
    private String payableAccountNumber;

    @Schema(description = "codigo eif")
    private String eifCode;

    @Schema(description = "tipo de transaccion")
    private Integer transactionType;

    @Schema(description = "numero de transaccion")
    private Long transactionNumber;

    @Schema(description = "monto de la transaccion")
    private BigDecimal amount;

    @Schema(description = "moneda de la transaccion")
    private Integer amountCurrency;

    @Schema(description = "monto equicalente al credito")
    private Integer equivalentCreditCurrency;

    @Schema(description = "monto equivalente al debito")
    private Integer equivalentDebitCurrency;

    @Schema(description = "titular de origen")
    private String originatingHolder;

    @Schema(description = "numero de cuenta  e origen")
    private String originatingAccountNumber;

    @Schema(description = "identificados de la cuenta de origen")
    private Integer originJtsNumber;

    @Schema(description = "nombre del titular")
    private String recipientHolder;

    @Schema(description = "identificador de la cuenta destino")
    private Long destinationJtsNumber;

    @Schema(description = "numero de cuenta la cuenta destino")
    private String destinationAccountNumber;

    @Schema(description = "monto de debito equivalente")
    private BigDecimal equivalentDebitAmount;

    @Schema(description = "tipo de cambio al debito")
    private BigDecimal debitExchangeRate;

    @Schema(description = "monto equivalente al credito")
    private BigDecimal equivalentCreditAmount;

    @Schema(description = "tipo de cambio al credito")
    private BigDecimal creditExchangeRate;

    @Schema(description = "descripcion")
    private String description;

    @Schema(description = "tiempo de publicacion")
    private String postingTime;

    @Schema(description = "identificador en la lista de cuentas")
    private String listIdAccount;

    @Schema(description = "nombre del banco destinatario")
    private String destinationBank;

}
