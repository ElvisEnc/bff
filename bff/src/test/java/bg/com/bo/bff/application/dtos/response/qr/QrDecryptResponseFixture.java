package bg.com.bo.bff.application.dtos.response.qr;

public class QrDecryptResponseFixture {
    public static QrDecryptResponse withDefault(){
        return QrDecryptResponse.builder()
                .qrId("1234567890")
                .companyName("Companhia Ejemplo")
                .identificationNumber("987654321")
                .eif("EIF Ejemplo")
                .accountNumber("123-456-789")
                .currency("BOB")
                .amount(150.75)
                .reference("Ref123456")
                .expirationDate("2024-12-31T23:59:59Z")
                .singleUse("0")
                .serviceCode(18001)
                .freeField("Pago por servicios")
                .serialNumber("SN123456789")
                .bank("Banco Ejemplo")
                .build();
    }
}
