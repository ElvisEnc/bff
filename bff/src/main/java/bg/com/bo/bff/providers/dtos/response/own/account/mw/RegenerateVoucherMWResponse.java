package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegenerateVoucherMWResponse {

    private List<Voucher> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Voucher{
        private String qrId;
        private String identificationNumber;
        private String businessName;
        private String expirationDate;
        private String freeField;
        private Integer singleUse;
        private String qrCurrency;
        private String serialNumber;
        private String payableAccountNumber;
        private String eifCode;
        private Integer transactionType;
        private Long transactionNumber;
        private BigDecimal amount;
        private Integer amountCurrency;
        private Integer equivalentCreditCurrency;
        private Integer equivalentDebitCurrency;
        private String originatingHolder;
        private String originatingAccountNumber;
        private Integer originJtsNumber;
        private String recipientHolder;
        private Long destinationJtsNumber;
        private String destinationAccountNumber;
        private BigDecimal equivalentDebitAmount;
        private BigDecimal debitExchangeRate;
        private BigDecimal equivalentCreditAmount;
        private BigDecimal creditExchangeRate;
        private String description;
        private String postingTime;
        private String listIdAccount;
        private String destinationBank;
    }

}
