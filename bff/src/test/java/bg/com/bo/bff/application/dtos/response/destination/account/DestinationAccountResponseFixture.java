package bg.com.bo.bff.application.dtos.response.destination.account;

import java.util.Arrays;

public class DestinationAccountResponseFixture {
    public static DestinationAccountResponse withDefault() {
        return DestinationAccountResponse.builder()
                .data(Arrays.asList(
                        DestinationAccount.builder()
                                .id(123456789L)
                                .accountId(123456789L)
                                .accountNumber(123456789L)
                                .currencyCode("UnitTest")
                                .currencyAcronym("UnitTest")
                                .clientName("UnitTest")
                                .bankCode("UnitTest")
                                .bankName("UnitTest")
                                .accountAliases("UnitTest")
                                .destinationAccountType(1)
                                .build()
                ))
                .total(17)
                .build();
    }

    public static DestinationAccount getDestinationAccountDefault() {
        return DestinationAccount.builder()
                .id(123456789L)
                .accountId(123456789L)
                .accountNumber(123456789L)
                .currencyCode("UnitTest")
                .currencyAcronym("UnitTest")
                .clientName("UnitTest")
                .bankCode("UnitTest")
                .bankName("UnitTest")
                .accountAliases("UnitTest")
                .destinationAccountType(1)
                .build();
    }
}