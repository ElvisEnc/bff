package bg.com.bo.bff.application.dtos.response.transfer;

import java.math.BigDecimal;

public class TransferResponseFixture {
    public static TransferResponse withDefault() {
        return TransferResponse.builder()
                .status("APPROVED")
                .idTransaction("123")
                .idMAE("123456")
                .accountingEntry("123")
                .accountingDate("2024-06-06")
                .accountingTime("10:00:00")
                .amountDebited(BigDecimal.valueOf(5.0))
                .amountCredited(BigDecimal.valueOf(5.0))
                .exchangeRateDebit(BigDecimal.valueOf(5.0))
                .exchangeRateCredit(BigDecimal.valueOf(5.0))
                .amount(BigDecimal.valueOf(5.0))
                .currency("068")
                .fromAccountNumber("123")
                .fromHolder("123")
                .toAccountNumber("123")
                .toHolder("123")
                .description("123")
                .fromCurrency("068")
                .toCurrency("068")
                .build();
    }
}