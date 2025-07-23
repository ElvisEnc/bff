package bg.com.bo.bff.providers.dtos.request.ach.account.mw;

import bg.com.bo.bff.providers.dtos.request.qr.mw.QrListMWRequest;

public class AchAccountMWRequestFixture {

    public static AddAchAccountBasicRequest withDefaultOKAddAchAccountBasicRequest() {
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

    public static DeleteAchAccountMWRequest withDefaultDeleteAchAccountMWRequest() {
        return new DeleteAchAccountMWRequest("123", "123");
    }

    public static QrListMWRequest withDefaultQrListMWRequest() {
        return QrListMWRequest.builder()
                .personId("123")
                .startDate("2024-01-30")
                .endDate("2024-01-31")
                .build();
    }
}
