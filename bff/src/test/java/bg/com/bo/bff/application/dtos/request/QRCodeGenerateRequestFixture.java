package bg.com.bo.bff.application.dtos.request;

public class QRCodeGenerateRequestFixture {
    public static QRCodeGenerateRequest whitDefault(){
        return QRCodeGenerateRequest.builder()
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

    }

}
