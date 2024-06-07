package bg.com.bo.bff.application.dtos.request.destination.account;

public class AddQRAccountRequestFixture {
    public static AddQRAccountRequest withDefaultOK() {
        return AddQRAccountRequest.builder()
                .accountNumber("123456789")
                .holderName("Prueba holder")
                .identificationNumber("123456789")
                .bankCode("1234")
                .reference("Referencia")
                .build();
    }
}
