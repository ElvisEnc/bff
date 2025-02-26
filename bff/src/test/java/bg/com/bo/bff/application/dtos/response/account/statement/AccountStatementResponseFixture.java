package bg.com.bo.bff.application.dtos.response.account.statement;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountStatementResponseFixture {
    public static List<AccountStatementsResponse> getDefaultAccountStatementsResponse() {
        return Collections.singletonList(AccountStatementsResponse.builder()
                .status("SUCCESS")
                .movementType("1")
                .amount(new BigDecimal("150.75"))
                .currencyCode("USD")
                .description("Pago de servicios")
                .balance(new BigDecimal("1200.00"))
                .movementDate("2024-04-30")
                .movementTime("14:41:13")
                .channel("ONLINE")
                .seatNumber("12345")
                .build());
    }

    public static List<TransferMovementsResponse> getDefaultTransferMovementsResponse() {
        return Collections.singletonList(TransferMovementsResponse.builder()
                .movementDate("19/07/2024")
                .movementTime("09:23:36")
                .operationNumber("7941")
                .toBankCode("1018")
                .accountId("123456788")
                .accountEntry("9990000000")
                .toAccountNumber("123456789")
                .toHolder("PERSONA JURIDICA")
                .description("Transferencia a 1213145. \r\nLOPEZnullPEREZnullCARLOSnullSAUL \r\nPCC01 ")
                .amount(BigDecimal.valueOf(80001.00))
                .abbreviated("0")
                .status("SUCCESS")
                .toBankName("BANCO GANADERO S.A.")
                .clientId("482307123")
                .build());
    }

    public static List<AccountStatementsResponse> getDefaultAccountStatementsResponseExport() {
        return Arrays.asList(AccountStatementsResponse.builder()
                        .status("1")
                        .movementType("D")
                        .amount(new BigDecimal("150.75"))
                        .currencyCode("068")
                        .description("Pago de servicios")
                        .balance(new BigDecimal("1200.00"))
                        .movementDate("2024-04-30")
                        .movementTime("14:41:13")
                        .channel("ONLINE")
                        .seatNumber("12345")
                        .build(),
                AccountStatementsResponse.builder()
                        .status("2")
                        .movementType("C")
                        .amount(new BigDecimal("150.75"))
                        .currencyCode("840")
                        .description("Pago de servicios")
                        .balance(new BigDecimal("1200.00"))
                        .movementDate("2024-04-30")
                        .movementTime("14:41:13")
                        .channel("ONLINE")
                        .seatNumber("12345")
                        .build());
    }


    public static List<AccountStatementsResponse> getDefaultAccountStatementsResponseExportCredit() {
        return Arrays.asList(AccountStatementsResponse.builder()
                        .status("1")
                        .movementType("C")
                        .amount(new BigDecimal("150.75"))
                        .currencyCode("068")
                        .description("Pago de servicios")
                        .balance(new BigDecimal("1200.00"))
                        .movementDate("2024-04-30")
                        .movementTime("14:41:13")
                        .channel("ONLINE")
                        .seatNumber("12345")
                        .build(),
                AccountStatementsResponse.builder()
                        .status("3")
                        .movementType("D")
                        .amount(new BigDecimal("150.75"))
                        .currencyCode("840")
                        .description("Pago de servicios")
                        .balance(new BigDecimal("1200.00"))
                        .movementDate("2024-04-30")
                        .movementTime("14:41:13")
                        .channel("ONLINE")
                        .seatNumber("12345")
                        .build());
    }
}