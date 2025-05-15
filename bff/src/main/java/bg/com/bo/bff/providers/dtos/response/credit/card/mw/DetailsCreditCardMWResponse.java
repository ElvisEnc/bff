package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailsCreditCardMWResponse {
    private String accountMaster;
    private String cmsAccountNumber;
    private String panNumber;
    private String holderName;
    private String type;
    private String placeCode;
    private String place;
    private String amountLimit;
    private BigDecimal amountAvailable;
    private String currencyCode;
    private String currencyDescription;
    private String dueDate;
    private String openDate;
    private String registrationDate;
    private String statusCard;
    private String statusAccount;
    private String insurance;
    private String limMin;
    private String limitMax;
    private String debtClose;
    private String dueDatePeriod;
    private String duePaymentDatePeriod;
    private String paymentAmountMinimum;
    private BigDecimal amountPaymentCurrent;
}
