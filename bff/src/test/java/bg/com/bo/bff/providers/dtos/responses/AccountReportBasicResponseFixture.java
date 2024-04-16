package bg.com.bo.bff.providers.dtos.responses;

import java.util.Collections;

public class AccountReportBasicResponseFixture {

    public static AccountReportBasicResponse withDefault() {
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
                        .processDate("processDate")
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