package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import java.math.BigDecimal;
import java.util.Collections;

public class OwnAccountMWResponseFixture {
    public static TransactionLimitListMWResponse withDefaultTransactionLimitListMWResponse(){
        return new TransactionLimitListMWResponse(
                TransactionLimitsMWResponse.builder()
                        .identifier("123445")
                        .transactionPermitDay(String.valueOf(10))
                        .availableTransaction(String.valueOf(1000))
                        .availableTransactionGroup("2")
                        .currencyCod("13")
                        .type("I")
                        .build()
        );
    }

    public static AccountStatementsMWResponse withDefaultAccountReportBasicResponse() {
        return AccountStatementsMWResponse.builder()
                .data(Collections.singletonList(AccountStatementsMWResponse.AccountStatementMW.builder()
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
                        .amount(BigDecimal.valueOf(10.00))
                        .agency("agency")
                        .city("city")
                        .accountingDate("accountingDate")
                        .processDate("2024-01-01")
                        .accountingTime("accountingTime")
                        .seatNumber(123465L)
                        .codBranchOffice("codBranchOffice")
                        .agencyCod("agencyCod")
                        .accountNumber("accountNumber")
                        .currencyCod("currencyCod")
                        .user("user")
                        .moveType("moveType")
                        .currentBalance(BigDecimal.valueOf(500.55))
                        .status("status")
                        .build()))
                .build();

    }
}