package bg.com.bo.bff.providers.dtos.request.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCreditCardMWRequest {
    private TransOwnerAccount ownerAccount;
    private TransAccount debtorAccount;
    private TransAccount creditorAccount;
    private TransAmount instructedAmount;
    private SupplementaryData supplementaryData;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SupplementaryData {
        private String transactionType;
        private String description;
        private String sourceOfFunds;
        private String destinationOfFunds;
    }
}
