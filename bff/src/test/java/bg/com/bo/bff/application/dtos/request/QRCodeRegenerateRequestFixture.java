package bg.com.bo.bff.application.dtos.request;

public class QRCodeRegenerateRequestFixture {
    public static QRCodeRegenerateRequest  withDefault(){
        return QRCodeRegenerateRequest
                .builder()
                .idQr("24042201018000000107111")
                .companyName("Solares")
                .identificationNumber("13639336111")
                .eif("1018")
                .accountNumber("1310771861111")
                .currency("BOB")
                .amount("50066")
                .reference("adas")
                .expirationDate("2024-04-29")
                .singleUse("0")
                .codService("1234")
                .field("casa")
                .build();
    }
}
