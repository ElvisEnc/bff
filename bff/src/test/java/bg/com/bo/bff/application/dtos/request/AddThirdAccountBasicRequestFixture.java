package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.providers.dtos.requests.AddThirdAccountBasicRequest;

public class AddThirdAccountBasicRequestFixture {
    public static AddThirdAccountBasicRequest withDefaultOK() {
        return AddThirdAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();

    }
}
