package bg.com.bo.bff.application.dtos.response.qr;

import java.math.BigDecimal;

public class QrDecryptResponseFixture {
    public static QrDecryptResponse withDefault(){
        return QrDecryptResponse.builder()
                .qrId("1234567890")
                .companyName("Companhia Ejemplo")
                .identificationNumber("987654321")
                .eif("EIF Ejemplo")
                .accountNumber("123-456-789")
                .currency("BOB")
                .amount(BigDecimal.valueOf(150.75))
                .reference("Ref123456")
                .expirationDate("2024-12-31")
                .singleUse("0")
                .serviceCode(18001)
                .freeField("Pago por servicios")
                .serialNumber("SN123456789")
                .bank("Banco Ejemplo")
                .build();
    }
}
