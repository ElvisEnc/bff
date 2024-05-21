package bg.com.bo.bff.providers.dtos.requests.login;

public class ChangePasswordMWRequestFixture {
    public static ChangePasswordMWRequest withDefault() {
        ChangePasswordMWRequest request = new ChangePasswordMWRequest();
        request.setPreviousPassword("1233");
        request.setNewPassword("1234");
        request.setOwnerAccount(MWOwnerAccountRequestFixture.withDefault());
        return request;
    }

}