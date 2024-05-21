package bg.com.bo.bff.providers.dtos.responses.login;

public class LoginMWFactorDataResponseFixture {
    public static LoginMWFactorDataResponse withDefault() {
        LoginMWFactorDataResponse loginMWFactorDataResponse = new LoginMWFactorDataResponse();
        loginMWFactorDataResponse.setPersonId("123");
        loginMWFactorDataResponse.setCodeImage("123");
        loginMWFactorDataResponse.setSecondFactor("123");
        loginMWFactorDataResponse.setIdGeneratorUuid("123");
        return loginMWFactorDataResponse;
    }
}