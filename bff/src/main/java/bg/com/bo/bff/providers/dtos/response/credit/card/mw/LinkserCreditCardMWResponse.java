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
public class LinkserCreditCardMWResponse {
    private List<LinkserCreditCardMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LinkserCreditCardMW {
        private String cmsCardNumber;
        private String statusCode;
        private String statusDescription;
        private String dueDate;
        private String cardType;
        private String panNumber;
        private String branch;
        private String cmsBaseNumber;
        private String additional;
        private String holderName;
    }
}
