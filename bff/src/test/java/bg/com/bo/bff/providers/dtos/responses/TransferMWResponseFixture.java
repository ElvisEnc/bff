package bg.com.bo.bff.providers.dtos.responses;

import java.math.BigDecimal;

public class TransferMWResponseFixture {
    public static TransferResponseMD withDefault() {
        return TransferResponseMD.builder()
                .data(TransferResponseMD.TransferMDData.builder()
                        .status("APPROVED")
                        .transferAchId("1010110101")
                        .accountingEntry("000000")
                        .accountingDate("05/05/2024")
                        .accountingTime("10:00:00")
                        .amount(BigDecimal.valueOf(5.0))
                        .amountCredited(BigDecimal.valueOf(5.0))
                        .amountDebited(BigDecimal.valueOf(5.0))
                        .currency("068")
                        .description("description")
                        .exchangeRateCredit(BigDecimal.valueOf(5.0))
                        .exchangeRateDebit(BigDecimal.valueOf(5.0))
                        .fromAccountNumber("111111111")
                        .fromCurrency("068")
                        .fromHolder("aaaaaaaa")
                        .idReceipt("1010110101")
                        .toAccountNumber("222222222")
                        .toCurrency("068")
                        .toHolder("bbbbbbbb")
                        .build())
                .build();
    }

    public static TransferResponseACHMD withDefaultACH() {
        return TransferResponseACHMD.builder()
                .data(TransferResponseACHMD.ResponseACH.builder()
                        .status("APPROVED")
                        .idReceipt("1010110101")
                        .transferAchId("1010110101")
                        .receiptDetail(TransferResponseACHMD.ResponseACH.ReceiptDetail.builder()
                                .accountingDate("05/05/2024")
                                .accountingTime("10:00:00")
                                .amount(BigDecimal.valueOf(5.0))
                                .amountCredited(BigDecimal.valueOf(5.0))
                                .amountDebited(BigDecimal.valueOf(5.0))
                                .currency("068")
                                .description("description")
                                .exchangeRateCredit(BigDecimal.valueOf(5.0))
                                .exchangeRateDebit(BigDecimal.valueOf(5.0))
                                .fromAccountNumber("111111111")
                                .fromCurrency("068")
                                .fromHolder("aaaaaaaa")
                                .toAccountNumber("222222222")
                                .toCurrency("068")
                                .toHolder("bbbbbbbb")
                                .build())
                        .build())
                .build();
    }
}