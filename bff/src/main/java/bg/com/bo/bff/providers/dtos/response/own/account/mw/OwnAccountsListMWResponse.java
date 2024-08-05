package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnAccountsListMWResponse {
    private List<AccountMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountMW {
        private String accountId;
        private String accountNumber;
        private String clientName;
        private String clientCode;
        private String accountHolderCode;
        private String currencyCode;
        private String currencyDescription;
        private String productDescription;
        private String accountManagementCode;
        private String accountType;
        private BigDecimal availiableBalance;
        private String accountManagementDescription;
        private String openingDate;
        private String dateOfLastMovement;
        private BigDecimal totalBalance;
        private BigDecimal pledgeFounds;
        private BigDecimal pendingDeposits;
        private String statusCode;
        private String statusDescription;
        private String branchCode;
        private String branchDescription;
        private String departamentCode;
        private String departamentDescription;
        private String accountUsage;
        private String accountUsageDescription;
        private String migrate;
    }
}
