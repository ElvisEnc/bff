package bg.com.bo.bff.providers.dtos.response.debit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDebitCardMWResponse {
    private List<DebitCardMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebitCardMW {
        private String idPci;
        private String cardId;
        private String department;
        private String nroClient;
        private String nroPerson;
        private String cardHolder;
        private String cardName;
        private String deliveryDate;
        private String expirationDate;
        private String statusDescription;
        private String status;
        private String protectionInsurance;
        private String insuranceAccountId;
        private String insuranceAccountNumber;
        private String statusCurrier;
    }
}
