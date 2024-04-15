package bg.com.bo.bff.application.dtos.requests;

import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;

public class AddThirdAccountRequestFixture {
    public static AddThirdAccountRequest withDefault(){
        return AddThirdAccountRequest.builder()
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }
}
