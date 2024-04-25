package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.application.dtos.request.AddAchAccountRequest;
import bg.com.bo.bff.application.dtos.request.AddThirdAccountRequest;

public class AddAchAccountRequestFixture {
    public static AddAchAccountRequest withDefault(){
        return AddAchAccountRequest.builder()
                .isFavorite("N")
                .isEnabled("S")
                .reference("referencia con espacios")
                .destinationAccountNumber("123456789")
                .destinationBankCode("123456789")
                .destinationBranchOfficeCode("123456789")
                .destinationAccountTypeCode("123456789")
                .destinationHolderName("123456789")
                .destinationIDNumber("123456789")
                .email("reynaldo@gmai.com")
                .build();
    }
}


