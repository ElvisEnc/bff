package bg.com.bo.bff.models.dtos.login;

public class LoginValidationServiceResponseFixture {
    public static LoginValidationServiceResponse withDefault() {
        LoginValidationServiceResponse response = new LoginValidationServiceResponse();
        response.setStatusCode("123");
        response.setPersonId("123");
        response.setUserDeviceId("123");
        response.setRolePersonId("123");
        return response;
    }
}