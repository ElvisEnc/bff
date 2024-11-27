package bg.com.bo.bff.providers.dtos.response.login.mw;

import bg.com.bo.bff.application.dtos.response.user.UserResponseFixture;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;

import java.util.Collections;

public class LoginMWResponseFixture {
    public static LoginValidationServiceResponse withDefaultLoginValidationServiceResponse() {
        LoginValidationServiceResponse response = new LoginValidationServiceResponse();
        response.setStatusCode("123");
        response.setPersonId("123");
        response.setUserDeviceId("123");
        response.setRolePersonId("123");
        return response;
    }

    public static ChangePasswordMWResponse withDefaultChangePasswordMWResponse() {
        return ChangePasswordMWResponse.builder()
                .personId("123")
                .build();
    }

    public static LogoutMWResponse withDefaultLogoutMWResponse() {
        return LogoutMWResponse.builder()
                .data(
                        LogoutMWResponse.LogoutData.builder()
                                .id("123")
                                .build()
                )
                .build();
    }

    public static LoginCredentialMWResponse withDefaultLoginCredentialMWResponse() {
        LoginCredentialMWResponse response = new LoginCredentialMWResponse();
        response.setData(withDefaultLoginCredentialData());
        return response;
    }

    public static LoginCredentialData withDefaultLoginCredentialData() {
        LoginCredentialData dataResponse = new LoginCredentialData();
        dataResponse.setSecurityImage("defaultSecurityImage");
        dataResponse.setSecondFactor("defaultSecondFactor");
        dataResponse.setKeyChange("defaultKeyChange");
        dataResponse.setKeyChangeMessage("defaultKeyChangeMessage");
        dataResponse.setUserEnrollmentId(123);
        dataResponse.setHolderName("Jorge");
        dataResponse.setRoleList(Collections.singletonList(roleWithDefault()));
        dataResponse.setLastConnectionDate("2024-05-16");
        dataResponse.setCodError("0000");
        dataResponse.setDesError("defaultDesError");
        return dataResponse;
    }

    public static LoginCredentialData.Role roleWithDefault() {
        LoginCredentialData.Role role = new LoginCredentialData.Role();
        role.setRolePersonId(1);
        role.setDescription("defaultDescription");
        role.setPersonId(1);
        role.setRelatedPersonId(2);
        role.setCompanyName("defaultCompanyName");
        role.setName("defaultName");
        role.setRoleCode(1);
        role.setUpdateUse(0);
        return role;
    }

    public static LoginFactorMWResponse withDefaultLoginFactorMWResponse() {
        return new LoginFactorMWResponse(withDefaultLoginFactorData());
    }

    public static LoginFactorData withDefaultLoginFactorData() {
        LoginFactorData loginMWFactorDataResponse = new LoginFactorData();
        loginMWFactorDataResponse.setPersonId("123");
        loginMWFactorDataResponse.setCodeImage("123");
        loginMWFactorDataResponse.setSecondFactor("123");
        loginMWFactorDataResponse.setIdGeneratorUuid("123");
        return loginMWFactorDataResponse;
    }

    public static BiometricStatusMWResponse withDefaultBiometricStatusMWResponse() {
        return BiometricStatusMWResponse.builder()
                .data(BiometricStatusMWResponse.BiometricStatusData.builder()
                        .statusBiometric("S")
                        .authenticationType("5")
                        .build())
                .build();
    }

    public static UpdateBiometricMWResponse withDefaultUpdateBiometricMWResponse() {
        return UpdateBiometricMWResponse.builder()
                .data(UserResponseFixture.withDefaultUpdateBiometricsResponse())
                .build();
    }
}