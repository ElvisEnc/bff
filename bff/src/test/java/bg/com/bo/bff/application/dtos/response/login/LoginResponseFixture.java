package bg.com.bo.bff.application.dtos.response.login;

import bg.com.bo.bff.models.TokenDataServiceResponse;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtHeader;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;

import java.util.ArrayList;

public class LoginResponseFixture {
    public static LoginValidationServiceResponse withDefaultLoginValidationServiceResponse() {
        return LoginValidationServiceResponse.builder()
                .statusCode("12345")
                .personId("123456")
                .userDeviceId("12345")
                .rolePersonId("12345")
                .name("Jorge")
                .fullName("Jorge Gonzales")
                .lastConnectionDate("12345")
                .keyChange(false)
                .keyChangeMessage("12345")
                .build();
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

    public static LoginResponse withDefaultLoginResponse() {
        return LoginResponse.builder()
                .accessToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRSmc5dDcxWDI0VVk2QXhJR09USTdHdVp0YS1aa2s3N0dNRlpIbm56U2NZIn0")
                .refreshToken("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5MjcyNjQ3ZS0wMGM4LTRhYTYtODljOC02NTBjMWQ2ZGVmYjMifQ")
                .expiresIn(600)
                .refreshExpiresIn(1800)
                .userData(
                        UserDataResponse.builder()
                                .userDeviceId("4804")
                                .rolePersonId("149688")
                                .name("ALVARO")
                                .personId("123456")
                                .fullName("ALVARO GUTIERREZ")
                                .build()
                )
                .lastConnectionDate("13/11/2024 16:42:31")
                .keyChange(false)
                .keyChangeMessage("Sugerencia de Cambio de Clave")
                .build();
    }

    public static LoginResult withDefaultLoginResult() {
        return LoginResult.builder()
                .personId("123456")
                .userDeviceId("123321")
                .rolePersonId("654321")
                .name("Jorge")
                .fullName("Jorge Perez")
                .lastConnectionDate("29/10/2024 15:35:53")
                .keyChange(false)
                .keyChangeMessage("Sugerencia de Cambio de Clave")
                .tokenData(TokenData.builder()
                        .accessToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRSmc5dDcxWDI0VVk2QXhJR09USTdHdVp0YS1aa2s3N0dNRlpIbm56U2NZIn0")
                        .refreshToken("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5MjcyNjQ3ZS0wMGM4LTRhYTYtODljOC02NTBjMWQ2ZGVmYjMifQ")
                        .expiresIn(600)
                        .refreshExpiresIn(1800)
                        .build())
                .statusCode(LoginResult.StatusCode.valueOf("SUCCESS"))
                .build();
    }

    public static DeviceEnrollmentResponse withDefaultDeviceEnrollmentResponse() {
        return DeviceEnrollmentResponse.builder()
                .personId("123456")
                .statusCode(1)
                .build();
    }

    public static TokenDataResponse withDefaultTokenDataResponse() {
        return TokenDataResponse.builder()
                .accessToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRSmc5dDcxWDI0VVk2QXhJR09USTdHdVp0YS1aa2s3N0dNRlpIbm56U2NZIn0")
                .refreshToken("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5MjcyNjQ3ZS0wMGM4LTRhYTYtODljOC02NTBjMWQ2ZGVmYjMifQ")
                .expiresIn(600)
                .refreshExpiresIn(1800)
                .build();
    }

    public static TokenData withDefaultTokenData() {
        return TokenData.builder()
                .accessToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJRSmc5dDcxWDI0VVk2QXhJR09USTdHdVp0YS1aa2s3N0dNRlpIbm56U2NZIn0")
                .refreshToken("eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI5MjcyNjQ3ZS0wMGM4LTRhYTYtODljOC02NTBjMWQ2ZGVmYjMifQ")
                .expiresIn(600)
                .refreshExpiresIn(1800)
                .build();
    }
}
