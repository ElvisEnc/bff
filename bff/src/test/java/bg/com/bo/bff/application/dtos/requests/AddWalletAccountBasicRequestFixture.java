package bg.com.bo.bff.application.dtos.requests;

import bg.com.bo.bff.providers.dtos.requests.AddWalletAccountBasicRequest;

public class AddWalletAccountBasicRequestFixture {
    public static AddWalletAccountBasicRequest withDefaultOK() {
        return AddWalletAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();

    }
}
