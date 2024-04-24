package bg.com.bo.bff.application.dtos.requests;

import bg.com.bo.bff.providers.dtos.requests.AddAchAccountBasicRequest;

public class AddAchAccountBasicRequestFixture {
    public static AddAchAccountBasicRequest withDefault(){
        return AddAchAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
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


