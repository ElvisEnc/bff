package bg.com.bo.bff.application.dtos.request;

import bg.com.bo.bff.providers.dtos.request.AddAchAccountBasicRequest;

public class AddAchAccountBasicRequestFixture {
    public static AddAchAccountBasicRequest withDefaultOK() {
        return AddAchAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
                .isFavorite("S")
                .isEnabled("S")
                .reference("Prueba")
                .destinationAccountNumber("123456789")
                .destinationBankCode("1234")
                .destinationBranchOfficeCode("SCZ")
                .destinationAccountTypeCode("CCAD")
                .destinationHolderName("Prueba holder")
                .destinationIDNumber("123456789")
                .email("email@gmail.com")
                .build();
    }
}
