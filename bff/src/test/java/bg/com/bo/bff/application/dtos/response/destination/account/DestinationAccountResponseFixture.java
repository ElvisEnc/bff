package bg.com.bo.bff.application.dtos.response.destination.account;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DestinationAccountResponseFixture {
    public static DestinationAccountResponse withDefault() {
        return DestinationAccountResponse.builder()
                .data(Arrays.asList(
                        DestinationAccount.builder()
                                .id(123456789L)
                                .accountId(123456789L)
                                .accountNumber(BigInteger.valueOf(123456789L))
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

    public static List<DestinationAccount> getDestinationAccountDefault() {
        return Collections.singletonList(DestinationAccount.builder()
                .id(1L)
                .accountId(123456789L)
                .accountNumber(BigInteger.valueOf(123456789L))
                .currencyCode("UnitTest")
                .currencyAcronym("UnitTest")
                .clientName("UnitTest")
                .bankCode("UnitTest")
                .bankName("UnitTest")
                .accountAliases("UnitTest")
                .destinationAccountType(1)
                .build());
    }

    public static BanksResponse withDefaultBanksResponse() {
        return new BanksResponse(
                List.of(
                        new Bank("ADM TARJETAS DE CREDITO A.T.C", "01111"),
                        new Bank("BANCO BISA", "01112")
                )
        );
    }

    public static BranchOfficeResponse withDefaultBranchOfficeResponse() {
        return BranchOfficeResponse.builder()
                .data(Arrays.asList(
                        BranchOfficeDataResponse.builder()
                                .id("001")
                                .description("Sucursal 1")
                                .build(),
                        BranchOfficeDataResponse.builder()
                                .id("002")
                                .description("Sucursal 2")
                                .build()
                ))
                .build();
    }

    public static ValidateAccountResponse withDefaultValidateAccountResponse() {
        return new ValidateAccountResponse(
                "6957474",
                "1310766620",
                "5219027",
                "EMPLEADO BANCO",
                "068",
                "701"
        );
    }

    public static AddAccountResponse withDefaultAddAccountResponse() {
        return AddAccountResponse.builder()
                .id(123456789L)
                .build();
    }

    public static DestinationAccount withDefaultDestinationAccount() {
        return DestinationAccount.builder()
                .id(123456789L)
                .accountId(123456789L)
                .accountNumber(BigInteger.valueOf(123456789L))
                .currencyCode("068")
                .currencyAcronym("UnitTest")
                .clientName("UnitTest")
                .bankCode("UnitTest")
                .bankName("UnitTest")
                .accountAliases("UnitTest")
                .destinationAccountType(1)
                .build();
    }
}