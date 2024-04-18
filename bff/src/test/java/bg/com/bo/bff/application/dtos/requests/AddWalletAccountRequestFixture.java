package bg.com.bo.bff.application.dtos.requests;

import bg.com.bo.bff.application.dtos.request.AddWalletAccountRequest;

public class AddWalletAccountRequestFixture {
    public static AddWalletAccountRequest withDefault(){
        return AddWalletAccountRequest.builder()
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }
}
