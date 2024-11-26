package bg.com.bo.bff.application.dtos.response.login;

import bg.com.bo.bff.models.TokenDataServiceResponse;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtHeader;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class LoginResponseFixture {
    public static LoginValidationServiceResponse withDefaultLoginValidationServiceResponse() {
        LoginValidationServiceResponse response = new LoginValidationServiceResponse();
        response.setStatusCode("12345");
        response.setPersonId("123456");
        response.setUserDeviceId("12345");
        response.setRolePersonId("12345");
        response.setLastConnectionDate("12345");
        response.setKeyChange(false);
        response.setKeyChangeMessage("12345");
        return response;
    }

    public static JwtRefresh withDefaultJwtRefresh() {
        JwtHeader jwtHeader = new JwtHeader();
        jwtHeader.setId("123");
        jwtHeader.setAlgorithm("SHA-256");
        jwtHeader.setTokenType("1");

        JwtPayload jwtPayload = new JwtPayload();
        jwtPayload.setExpiration("360");
        jwtPayload.setIssuedAt("123");
        jwtPayload.setId("123");
        jwtPayload.setIssuer("123");
        jwtPayload.setAudience("123");
        jwtPayload.setSubject("123");
        jwtPayload.setType("123");
        jwtPayload.setAuthorizedParty("123");
        jwtPayload.setSessionId("123");
        jwtPayload.setClientId("123");
        jwtPayload.setRoles(new ArrayList<>());
        jwtPayload.setPersonId("123456");
        jwtPayload.setSid("123");

        JwtRefresh jwtRefresh = new JwtRefresh();
        jwtRefresh.setPayload(jwtPayload);
        jwtRefresh.setHeader(jwtHeader);
        return jwtRefresh;
    }

    public static CreateTokenServiceResponse withDefaultCreateTokenServiceResponse() {
        TokenDataServiceResponse tokenDataServiceResponse = new TokenDataServiceResponse();
        tokenDataServiceResponse.setAccessToken("123456");
        tokenDataServiceResponse.setRefreshToken("123456");
        tokenDataServiceResponse.setExpiresIn(360);
        tokenDataServiceResponse.setRefreshExpiresIn(360);

        CreateTokenServiceResponse createTokenServiceResponse = new CreateTokenServiceResponse();
        createTokenServiceResponse.setTokenData(tokenDataServiceResponse);
        createTokenServiceResponse.setStatusCode(CreateTokenServiceResponse.StatusCode.SUCCESS);
        return createTokenServiceResponse;
    }

    public static RefreshSessionResult withDefaultRefreshSessionResult() {
        RefreshSessionResult result = new RefreshSessionResult();
        result.setAccessToken("123");
        result.setRefreshToken("123");
        result.setExpiresIn(360);
        result.setRefreshExpiresIn(360);
        result.setStatusCode(RefreshSessionResult.StatusCode.SUCCESS);
        return result;
    }

    public static DeviceEnrollmentMWResponse withDefaultDeviceEnrollmentMWResponse() {
        DeviceEnrollmentMWResponse deviceEnrollmentMWResponse = new DeviceEnrollmentMWResponse();
        deviceEnrollmentMWResponse.setStatusCode("ENROLLED");
        deviceEnrollmentMWResponse.setPersonId("personId123");
        return deviceEnrollmentMWResponse;
    }

    public static DeviceEnrollmentMWResponse withDefaultDeviceEnrollmentMWResponseNotEnrolled() {
        DeviceEnrollmentMWResponse deviceEnrollmentMWResponse = new DeviceEnrollmentMWResponse();
        deviceEnrollmentMWResponse.setStatusCode("NOT_ENROLLED");
        return deviceEnrollmentMWResponse;
    }
}
