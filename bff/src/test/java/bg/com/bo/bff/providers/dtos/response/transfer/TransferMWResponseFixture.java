package bg.com.bo.bff.providers.dtos.response.transfer;

import java.math.BigDecimal;

public class TransferMWResponseFixture {
    public static TransferMWResponse withDefault() {
        return TransferMWResponse.builder()
                .data(TransferMWResponse.TransferMWData.builder()
                        .status("APPROVED")
                        .idReceipt("1010110101")
                        .idMaeTransaction("1010110101")
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

    public static TransferAchMwResponse withDefaultACH() {
        return TransferAchMwResponse.builder()
                .data(TransferAchMwResponse.ResponseAch.builder()
                        .status("APPROVED")
                        .transferAchId("1010110101")
                        .maeIdTransaction("1010110101")
                        .receiptDetail(TransferAchMwResponse.ResponseAch.ReceiptDetail.builder()
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

    public static TransferWalletMWResponse withDefaultWallet() {
        return TransferWalletMWResponse.builder()
                .data(TransferWalletMWResponse.TransferMWData.builder()
                        .status("APPROVED")
                        .nroTransaction("1010110101")
                        .identifierMae("1010110101")
                        .receiptDetail(TransferWalletMWResponse.TransferMWData.ReceiptDetail.builder()
                                .accountingDate("05/05/2024")
                                .accountingTime("10:00:00")
                                .amount(5.0)
                                .amountCredited(5.0)
                                .amountDebited(5.0)
                                .currency("068")
                                .description("description")
                                .exchangeRateCredit(5.0)
                                .exchangeRateDebit(5.0)
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