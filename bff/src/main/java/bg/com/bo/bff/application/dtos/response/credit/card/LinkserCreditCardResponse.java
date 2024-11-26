package bg.com.bo.bff.application.dtos.response.credit.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkserCreditCardResponse {
    private String cmsCard;
    private String cardNumber;
    private String holderName;
    private String dueDate;
    private String branch;
    private String cardType;
    private String statusCode;
    private String statusDescription;
}
