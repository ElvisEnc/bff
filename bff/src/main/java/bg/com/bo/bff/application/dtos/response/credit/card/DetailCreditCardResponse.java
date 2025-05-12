package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailCreditCardResponse {
    @Schema(description = "id de la tarjeta")
    private String cardId;

    @Schema(description = "cms de la cuenta")
    private String cmsAccount;

    @Schema(description = "número de la tarjeta")
    private String cardNumber;

    @Schema(description = "número principal de la tarjeta.")
    private String panNumber;

    @Schema(description = "nombre del titular")
    private String holderName;

    @Schema(description = "código de plaza")
    private String placeCode;

    @Schema(description = "plaza")
    private String place;

    @Schema(description = "fecha de vencimiento")
    private String dueDate;

    @Schema(description = "fecha de registro")
    private String registrationDate;

    @Schema(description = "estado de la tarjeta")
    private String cardStatus;

    @Schema(description = "estado de la cuenta")
    private String accountStatus;

    @Schema(description = "seguro")
    private Boolean insurance;

    @Schema(description = "código de moneda")
    private String currency;

    @Schema(description = "monto dispobible")
    private String amountAvailable;

    @Schema(description = "Monto límite.")
    private String amountLimit;

    @Schema(description = "límite minimo")
    private String limitMin;

    @Schema(description = "límite máximo")
    private String limitMax;

    @Schema(description = "Debito cerrado.")
    private String debtClose;

    @Schema(description = "FechaVencimiento pago")
    private String dueDatePeriod;

    @Schema(description = "DuePayment Date Perio")
    private String duePaymentDatePeriod;

    @Schema(description = "Monto minimo de pago")
    private String paymentAmountMinimum;
}
