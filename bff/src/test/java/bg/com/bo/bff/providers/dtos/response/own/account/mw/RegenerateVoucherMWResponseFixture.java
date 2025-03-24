package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import java.math.BigDecimal;
import java.util.Arrays;

public class RegenerateVoucherMWResponseFixture {

    public static RegenerateVoucherMWResponse withDefaults() {
        return RegenerateVoucherMWResponse.builder()
                .data(
                        Arrays.asList(
                                RegenerateVoucherMWResponse.Voucher.builder()
                                        .qrId("")
                                        .identificationNumber("")
                                        .businessName("")
                                        .expirationDate(null)
                                        .freeField("")
                                        .singleUse(0)
                                        .qrCurrency("")
                                        .serialNumber("")
                                        .payableAccountNumber("")
                                        .eifCode("")
                                        .transactionType(3)
                                        .transactionNumber(54468839L)
                                        .amount(new BigDecimal("1.0"))
                                        .amountCurrency(0)
                                        .equivalentCreditCurrency(0)
                                        .equivalentDebitCurrency(0)
                                        .originatingHolder("EMPLEADO BANCO")
                                        .originatingAccountNumber("1310766620")
                                        .originJtsNumber(6957474)
                                        .recipientHolder("EMPLEADO BANCO")
                                        .destinationJtsNumber(4355307L)
                                        .destinationAccountNumber("1310325715")
                                        .equivalentDebitAmount(new BigDecimal("1.0"))
                                        .debitExchangeRate(new BigDecimal("1.0"))
                                        .equivalentCreditAmount(new BigDecimal("1.0"))
                                        .creditExchangeRate(new BigDecimal("1.0"))
                                        .description("Pago de servicios")
                                        .postingTime("11:31:03")
                                        .listIdAccount("33")
                                        .build()
                        )
                )
                .build();
    }

}
