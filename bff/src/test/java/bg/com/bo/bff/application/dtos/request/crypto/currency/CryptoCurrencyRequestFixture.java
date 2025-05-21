package bg.com.bo.bff.application.dtos.request.crypto.currency;


public class CryptoCurrencyRequestFixture {

    public static AccountExtractRequest withDefaultAccountExtract() {
        return AccountExtractRequest.builder()
                .accountNumber("558745")
                .startDate("2025-01-01")
                .endDate("2025-01-31")
                .startPage(1)
                .endPage(100)
                .build();
    }

    public static ExchangeOperationRequest withDefaultExchangeOperation() {
        return ExchangeOperationRequest.builder()
                .amount(78.5)
                .currency("2222")
                .destinationAccount("27845")
                .description("test")
                .build();
    }

    public static GenerateVoucherRequest withDefaultGenerateVoucher() {
        return GenerateVoucherRequest.builder()
                .seatNumber("2222")
                .dateProcess("2025-01-31")
                .build();
    }
}
