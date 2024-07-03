package bg.com.bo.bff.application.dtos.response.account.statement;

import java.util.Collections;

public class AccountStatementResponseFixture {
    public static AccountStatementExtractResponse withDefaultAccountStatementExtractResponse() {
        return AccountStatementExtractResponse.builder()
                .data(Collections.singletonList(AccountStatementExtractResponse.AccountStatementExtract.builder()
                        .status("status")
                        .type("type")
                        .amount(100.00)
                        .currency("currency")
                        .channel("channel")
                        .dateMov("dateMov")
                        .timeMov("timeMov")
                        .movBalance(987.99)
                        .seatNumber("seatNumber")
                        .build()))
                .build();
    }
}