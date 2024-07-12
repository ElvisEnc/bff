package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import java.math.BigDecimal;

public record PaymentDebtsMWResponse(
        String status,
        String maeId,
        String nroTransaction,
        ReceiptDetail receiptDetail

) {
    public record ReceiptDetail(
            String accountingDate,
            String accountingTime,
            String accountingEntry,
            String fromAccountNumber,
            String fromHolder,
            BigDecimal amount,
            String currency,
            BigDecimal exchangeAmount,
            String fromAccountCurrency,
            String exchangeRateDebit,
            String company,
            String affiliationNumber,
            String description,
            String servicePaymentCode,
            String voucherId
    ) {
    }
}
