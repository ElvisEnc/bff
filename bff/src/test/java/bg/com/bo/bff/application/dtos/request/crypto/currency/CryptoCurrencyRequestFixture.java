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
}
