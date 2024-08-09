package bg.com.bo.bff.providers.dtos.request.third.account.mw;

public class ThirdAccountMWRequestFixture {

    public static AddThirdAccountBasicRequest withDefaultOKAddThirdAccountBasicRequest() {
        return AddThirdAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }

    public static AddWalletAccountBasicRequest withDefaultOKAddWalletAccountBasicRequest() {
        return AddWalletAccountBasicRequest.builder()
                .personId("12345")
                .companyPersonId("12345")
                .toAccountNumber("123456789")
                .reference("Prueba")
                .isFavorite("S")
                .build();
    }

    public static DeleteThirdAccountMWRequest withDefaultDeleteThirdAccountMWRequest() {
        return DeleteThirdAccountMWRequest.builder()
                .personId("123")
                .identifier("123")
                .accountNumber("123")
                .build();
    }
}
