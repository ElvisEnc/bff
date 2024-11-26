package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPrepaidCardMWResponse {
    private String accountMaster;
    private String cmsAccountNumber;
    private String panNumber;
    private String holderName;
    private String productCode;
    private String product;
    private String currency;
    private String currencyDescription;
    private String registrationDate;
    private String dueDate;
    private String statusCard;
    private String statusAccount;
    private String placeCode;
    private String place;
    private String insurance;
    private String limitMin;
    private String limitMax;
}
