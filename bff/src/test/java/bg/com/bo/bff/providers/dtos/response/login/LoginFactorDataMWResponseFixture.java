package bg.com.bo.bff.providers.dtos.response.login;

public class LoginFactorDataMWResponseFixture {
    public static LoginFactorData withDefault() {
        LoginFactorData loginMWFactorDataResponse = new LoginFactorData();
        loginMWFactorDataResponse.setPersonId("123");
        loginMWFactorDataResponse.setCodeImage("123");
        loginMWFactorDataResponse.setSecondFactor("123");
        loginMWFactorDataResponse.setIdGeneratorUuid("123");
        return loginMWFactorDataResponse;
    }
}