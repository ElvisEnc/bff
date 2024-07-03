package bg.com.bo.bff.providers.dtos.request.qr.mw;

public class QrMWRequestFixture {
    public static QRCodeRegenerateMWRequest withDefaultQRCodeRegenerateMWRequest() {
        return new QRCodeRegenerateMWRequest("ddsfs");
    }

    public static QRCodeGenerateMWRequest withDefaultQRCodeGenerateMWRequest() {
        QRCodeGenerateMWRequest request = QRCodeGenerateMWRequest.builder()
                .companyName("example")
                .amount("1.00")
                .currency("068")
                .reference("adas")
                .typeDueDate("1")
                .codService("0")
                .singleUse("1310771861")
                .accountNumber("1")
                .field("1")
                .serialNumber("1")
                .operationType("1")
                .personId("172628")
                .userRegister("1")
                .typeReturn("S")
                .formatImage("1")
                .build();
        request.setOwnerAccount("PersonId", request.getPersonId());
        return request;
    }
}