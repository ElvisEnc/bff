package bg.com.bo.bff.providers.dtos.response.own.account.mw;

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
public class AccountStatementsMWResponse {
    private List<AccountStatementMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountStatementMW {
        private String reportMoveType;
        private String destinationAccount;
        private String orderNumber;
        private String branchOffice;
        private String cityCode;
        private String description;
        private String operationDescription;
        private String operation;
        private String startDate;
        private String startTime;
        private BigDecimal amount;
        private String agency;
        private String city;
        private String accountingDate;
        private String processDate;
        private String accountingTime;
        private Long seatNumber;
        private String codBranchOffice;
        private String agencyCod;
        private String accountNumber;
        private String currencyCod;
        private String user;
        private String moveType;
        private BigDecimal currentBalance;
        private String status;
    }
}
