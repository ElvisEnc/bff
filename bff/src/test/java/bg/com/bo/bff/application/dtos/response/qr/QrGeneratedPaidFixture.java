package bg.com.bo.bff.application.dtos.response.qr;

import java.math.BigDecimal;

public class QrGeneratedPaidFixture {
    public static QrGeneratedPaid withDefault() {
        return QrGeneratedPaid.builder()
                .customField("1")
                .qrId("24041901018000000159")
                .masterQrId("74305283")
                .identificationNumber("2920504")
                .businessName("UnitTest")
                .bankCode("1018")
                .bank("Banco")
                .currencyCode("068")
                .expiryDate("26/04/2024")
                .amount(BigDecimal.valueOf(100.50))
                .description("UnitTest")
                .singleUse(true)
                .serviceCode("1")
                .serialNumber("1")
                .operationType("1")
                .destinationAccountNumber(1310771861L)
                .status("2")
                .registrationDate("2024-04-19")
                .currencyDescription("UnitTest")
                .build();
    }
}