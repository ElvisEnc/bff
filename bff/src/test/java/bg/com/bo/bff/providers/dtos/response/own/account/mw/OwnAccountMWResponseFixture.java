package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import java.util.Collections;

public class OwnAccountMWResponseFixture {
    public static TransactionLimitListMWResponse withDefaultTransactionLimitListMWResponse(){
        return new TransactionLimitListMWResponse(
                TransactionLimit.builder()
                        .identifier("123445")
                        .transactionPermitDay(10)
                        .availableTransaction(1000)
                        .availableTransactionGroup("2")
                        .currencyCod("13")
                        .type("I")
                        .build()
        );
    }

    public static AccountReportBasicResponse withDefaultAccountReportBasicResponse() {
        return AccountReportBasicResponse.builder()
                .data(Collections.singletonList(AccountReportBasicResponse.AccountReportData.builder()
                        .reportMoveType("reportMoveType")
                        .destinationAccount("destinationAccount")
                        .orderNumber("orderNumber")
                        .branchOffice("branchOffice")
                        .cityCode("cityCode")
                        .description("description")
                        .operationDescription("operationDescription")
                        .operation("operation")
                        .startDate("startDate")
                        .startTime("startTime")
                        .amount(10.00)
                        .agency("agency")
                        .city("city")
                        .accountingDate("accountingDate")
                        .processDate("2024-01-01")
                        .accountingTime("accountingTime")
                        .seatNumber(123465)
                        .codBranchOffice("codBranchOffice")
                        .agencyCod("agencyCod")
                        .accountNumber("accountNumber")
                        .currencyCod("currencyCod")
                        .user("user")
                        .moveType("moveType")
                        .currentBalance(500.55)
                        .status("status")
                        .build()))
                .build();

    }
}