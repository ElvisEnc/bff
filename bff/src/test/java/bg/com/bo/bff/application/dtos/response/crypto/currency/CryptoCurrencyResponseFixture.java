package bg.com.bo.bff.application.dtos.response.crypto.currency;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;

public class CryptoCurrencyResponseFixture {

    public static AvailableBalanceResponse withDefaultAvailableBalance() {
        return AvailableBalanceResponse.builder()
                .currency("2222")
                .availableBalance(478.5)
                .account(14785)
                .status("ok")
                .jtsOid(7845)
                .product("ok")
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
                .name("test")
                .build();
    }

    public static AccountExtractResponse withDefaultAccountExtract() {
        return AccountExtractResponse.builder()
                .existsVoucher(false)
                .transactionDate("2025-01-01")
                .transactionTime("00:00:00")
                .amount("78.2")
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

    public static ExchangeRateResponse withDefaultExchangeRate() {
        return ExchangeRateResponse.builder()
                .purchaseFxRate(12.2)
                .saleFxRate(12.2)
                .description("test")
                .build();
    }

    public static ExchangeOperationResponse withDefaultExchangeOperation() {
        return ExchangeOperationResponse.builder()
                .importDebited(78.5)
                .currency(5)
                .seatNo(5L)
                .receiptId(5)
                .tcCredit(5)
                .tcDebit(5.5)
                .branch(5)
                .dateSeat("test")
                .importItf(5.5)
                .importAccredited(5.5)
                .build();
    }

    public static GenerateVoucherResponse withDefaultGenerateVoucher() {
        return GenerateVoucherResponse.builder()
                .currencyDebit(1)
                .currency(20)
                .build();
    }

}
