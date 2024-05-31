package bg.com.bo.bff.providers.dtos.request.login;

public class MWOwnerAccountRequestFixture {
    public static MWOwnerAccountRequest withDefault() {
        MWOwnerAccountRequest ownerAccountRequest = new MWOwnerAccountRequest();
        ownerAccountRequest.setPersonId("123");
        ownerAccountRequest.setPersonRoleId("123");
        ownerAccountRequest.setUserDeviceId("123");
        return ownerAccountRequest;
    }
}