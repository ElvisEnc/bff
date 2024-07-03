package bg.com.bo.bff.providers.dtos.response.qr.mw;

import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrGeneratedPaidMW;
import bg.com.bo.bff.providers.dtos.response.ach.account.mw.QrListMWResponse;

import java.math.BigDecimal;
import java.util.Arrays;

public class QrMWResponseFixture {
    public static QrListMWResponse withDefaultQrListMWResponse() {
        return QrListMWResponse.builder()
                .data(Arrays.asList(
                        QrGeneratedPaidMW.builder()
                                .customField("1")
                                .masterQrId("74305283")
                                .qrId("24041901018000000159")
                                .identificationNumber("2920504")
                                .bussinesName("UnitTest")
                                .bankCode("1018")
                                .currency("068")
                                .expiryDate("26/04/2024")
                                .reference("UnitTest")
                                .monto(BigDecimal.valueOf(100.50))
                                .singleUse("1")
                                .serviceCode("1")
                                .serialNumber("1")
                                .operationTypeId("1")
                                .accountNumber("1310771861")
                                .status("2")
                                .registrationDate("2024-04-19")
                                .description("UnitTest")
                                .currencyDescription("UnitTest")
                                .build()
                ))
                .build();
    }
}