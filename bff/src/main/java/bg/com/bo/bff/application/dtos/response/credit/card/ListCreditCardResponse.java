package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListCreditCardResponse {
    private List<CreditCardResponse> creditCards;
    private List<PrepaidCardResponse> prepaidCards;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCardResponse {
        @Schema(description = "id de la tarjeta")
        private String cardId;

        @Schema(description = "producto")
        private String product;

        @Schema(description = "número de la tarjeta")
        private String cardNumber;

        @Schema(description = "número de cuenta compuesto")
        private String cmsAccount;

        @Schema(description = "nombre del titular")
        private String holderName;

        @Schema(description = "código de moneda")
        private String currency;

        @Schema(description = "fecha de vencimiento")
        private String dueDate;

        @Schema(description = "monto límite")
        private String limitAmount;

        @Schema(description = "tipo de tarjeta")
        private String typeCard;

        @Schema(description = "código de cliente")
        private String clientCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrepaidCardResponse {
        @Schema(description = "id de la tarjeta")
        private String cardId;

        @Schema(description = "producto")
        private String product;

        @Schema(description = "número de la tarjeta")
        private String cardNumber;

        @Schema(description = "número de cuenta compuesto")
        private String cmsAccount;

        @Schema(description = "nombre del titular")
        private String holderName;

        @Schema(description = "código de moneda")
        private String currency;

        @Schema(description = "fecha de vencimiento")
        private String dueDate;

        @Schema(description = "tipo de tarjeta")
        private String typeCard;

        @Schema(description = "código de cliente")
        private String clientCode;

        @Schema(description = "estado")
        private String status;

        @Schema(description = "fecha de solicitud")
        private String solicitudeDate;

        @Schema(description = "código de tipo de tarjeta")
        private String typeCardCode;
    }
}
