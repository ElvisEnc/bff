package bg.com.bo.bff.application.dtos.response.destination.account;

import java.math.BigInteger;
import java.util.Arrays;
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

    public static DestinationAccount getDestinationAccountDefault() {
        return DestinationAccount.builder()
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
                .build();
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

}