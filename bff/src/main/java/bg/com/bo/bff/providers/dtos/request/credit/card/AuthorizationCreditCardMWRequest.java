package bg.com.bo.bff.providers.dtos.request.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCreditCardMWRequest {
    private String personId;
    private String cmsAccountNumber;
    private String cmsCardNumber;
    private String initDate;
    private String endDate;
    private String amount;
    private String type;
    private String requestType;
}
