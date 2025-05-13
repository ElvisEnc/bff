package bg.com.bo.bff.application.dtos.response.crypto.currency;


import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

import java.math.BigDecimal;

public class CryptoCurrencyResponseFixture {

    public static AvailableBalanceResponse withDefaultAvailableBalance() {
        return AvailableBalanceResponse.builder()
                .currency("2222")
                .availableBalance(BigDecimal.valueOf(478.5))
                .account(14785)
                .status("ok")
                .build();
    }

    public static GenericResponse withDefaultGeneric() {
        return GenericResponse.builder()
                .code("OK")
                .message("OK")
                .title("OK")
                .build();
    }

    public static AccountEmailResponse withDefaultAccountEmail() {
        return AccountEmailResponse.builder()
                .email("test@test.com")
                .build();
    }

    public static AccountExtractResponse withDefaultAccountExtract() {
        return AccountExtractResponse.builder()
                .existsVoucher(false)
                .transactionDate("2025-01-01")
                .transactionTime("00:00:00")
                .amount(BigDecimal.ZERO)
                .description("Default transaction")
                .day(1)
                .month("January")
                .year("2025")
                .transactionType("DEFAULT")
                .processDate("2025-01-01")
                .branch("000")
                .seatNumber("0000")
                .correlative("000000")
                .currentBalance("0.00")
                .currencySymbol("$")
                .build();
    }

}
