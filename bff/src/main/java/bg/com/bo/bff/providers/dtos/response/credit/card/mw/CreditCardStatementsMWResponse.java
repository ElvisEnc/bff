package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

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
public class CreditCardStatementsMWResponse {
    private List<CreditCardStatementMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreditCardStatementMW {
        private String transactionDate;
        private String processDate;
        private String description;
        private String currency;
        private String transactionTypeDesc;
        private String clientName;
        private String transactionStatus;
        private String originalCurrency;
        private BigDecimal mrAmount;
        private BigDecimal mlAmount;
        private String transactionType;
        private String cardNumber;
        private BigDecimal sequenceNumber;
        private String feeNumber;
        private String paramDate;
    }
}
