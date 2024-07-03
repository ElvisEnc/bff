package bg.com.bo.bff.providers.dtos.response.debit.card.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsDebitCardMWResponse {
    private List<AccountDebitCard> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDebitCard {
        private Integer jtsOid;
        private BigDecimal accountNumber;
        private String productType;
        private String statusDescription;
        private String currency;
        private Integer pledgeFund;
        private String currencyCode;
        private String accountType;
        private Integer ordinalPreference;
    }
}
