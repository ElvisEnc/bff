package bg.com.bo.bff.providers.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountReportBasicResponse {
    private List<AccountReportData> data;

    @lombok.Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountReportData {
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
        private Double amount;
        private String agency;
        private String city;
        private String accountingDate;
        private String processDate;
        private String accountingTime;
        private Integer seatNumber;
        private String codBranchOffice;
        private String agencyCod;
        private String accountNumber;
        private String currencyCod;
        private String user;
        private String moveType;
        private Double currentBalance;
        private String status;
    }
}
