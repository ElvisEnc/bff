package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

public class OwnAccountMWResponseFixture {
    public static OwnAccountsListMWResponse withDefaultOwnAccountsListMWResponse() {
        return OwnAccountsListMWResponse.builder()
                .data(Collections.singletonList(withDefaultAccountMW()))
                .build();
    }

    public static OwnAccountsListMWResponse.AccountMW withDefaultAccountMW() {
        return OwnAccountsListMWResponse.AccountMW.builder()
                .accountId("123456789")
                .accountNumber("987654321")
                .clientName("Juan Perez")
                .clientCode("C123456")
                .accountHolderCode("AH123456")
                .currencyCode("USD")
                .currencyDescription("Dólares Americanos")
                .productDescription("Cuenta Corriente")
                .accountManagementCode("MGMT123")
                .accountType("Corriente")
                .availiableBalance(new BigDecimal("1500.00"))
                .accountManagementDescription("Gestión Personal")
                .openingDate("2024-01-01")
                .dateOfLastMovement("2024-07-01")
                .totalBalance(new BigDecimal("2000.00"))
                .pledgeFounds(new BigDecimal("500.00"))
                .pendingDeposits(new BigDecimal("300.00"))
                .statusCode("ACTIVE")
                .statusDescription("Activa")
                .branchCode("001")
                .branchDescription("Sucursal Principal")
                .departamentCode("D01")
                .departamentDescription("Departamento Central")
                .accountUsage("Personal")
                .accountUsageDescription("Uso Personal")
                .migrate("NO")
                .build();
    }

    public static OwnAccountsListMWResponse withDefaultOwnAccountsListMWResponseNull() {
        return OwnAccountsListMWResponse.builder()
                .data(null)
                .build();
    }

    public static UpdateLimitMWResponse withDefaultUpdateLimitMWResponse() {
        return new UpdateLimitMWResponse(new UpdateLimitMWResponse.UpdateLimitMW("123"));
    }


    public static TransactionLimitsMWResponse withDefaultTransactionLimitsMWResponse() {
        return TransactionLimitsMWResponse.builder()
                .identifier("123")
                .transactionPermitDay("10")
                .transactionsRegisteredInDay("10")
                .availableTransaction("10")
                .availableTransactionGroup("10")
                .currencyCod("068")
                .type("I")
                .build();
    }

    public static AccountStatementsMWResponse withDefaultAccountReportBasicResponse() {
        return AccountStatementsMWResponse.builder()
                .data(Arrays.asList(AccountStatementsMWResponse.AccountStatementMW.builder()
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
                                .moveType("C")
                                .currentBalance(BigDecimal.valueOf(500.55))
                                .status("status")
                                .build(),
                        AccountStatementsMWResponse.AccountStatementMW.builder()
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
                                .moveType("D")
                                .currentBalance(BigDecimal.valueOf(500.55))
                                .status("status")
                                .build()
                ))
                .build();
    }

    public static AccountStatementsMWResponse withDefaultAccountReportBasicResponseNull() {
        return AccountStatementsMWResponse.builder()
                .data(null)
                .build();
    }
}