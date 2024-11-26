package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListCreditCardMWResponse {
    private List<CreditCardMWResponse> creditCards;
    private List<PrepaidCardMWResponse> prepaidCards;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCardMWResponse {
        private String accountMaster;
        private String product;
        private String panNumber;
        private String cmsAccountNumber;
        private String holderName;
        private String currency;
        private String currencyDescription;
        private String dueDate;
        private String limitAmount;
        private String typeCard;
        private String clientCode;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrepaidCardMWResponse {
        private String accountMaster;
        private String product;
        private String panNumber;
        private String cmsAccountNumber;
        private String holderName;
        private String currency;
        private String currencyDescription;
        private String solicitudeDate;
        private String typeCardCode;
        private String dueDate;
        private String status;
        private String typeCard;
        private String clientCode;
    }
}
