package bg.com.bo.bff.providers.dtos.request.crypto.currency;

import bg.com.bo.bff.application.dtos.request.crypto.currency.AccountExtractRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.ExchangeOperationRequest;
import bg.com.bo.bff.application.dtos.request.crypto.currency.GenerateVoucherRequest;
public class CryptoCurrencyRequestDTOFixture {

    public static AccountExtractRequest withDefaultAccountExtract() {
        return AccountExtractRequest.builder()
                .accountNumber("99000214")
                .startDate("05/05/2025")
                .endDate("05/05/2025")
                .startPage(1)
                .endPage(100)
                .build();
    }

    public static GenerateVoucherRequest withDefaultGenerateVoucher() {
        return GenerateVoucherRequest.builder()
                .seatNumber("99000214")
                .dateProcess("05/05/2025")
                .build();
    }

    public static ExchangeOperationRequest withDefaultExchangeOperation() {
        return ExchangeOperationRequest.builder()
                .amount(7.8)
                .currency("2222")
                .destinationAccount("78954")
                .description("description")
                .build();
    }
}
