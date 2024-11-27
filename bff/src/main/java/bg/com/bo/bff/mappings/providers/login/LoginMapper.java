package bg.com.bo.bff.mappings.providers.login;

import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.login.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorData;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginMapper implements ILoginMapper {
    public LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data) {
        return LoginCredentialMWRequest.builder()
                .personId(data.getPersonId())
                .password(loginRequest.getPassword())
                .idGeneratorUuid(data.getIdGeneratorUuid())
                .loginType(loginRequest.getType())
                .tokenFinger(loginRequest.getTokenBiometric())
                .build();
    }

    @Override
    public LogoutMWRequest mapperRequest(String personId, String personRoleId) {
        return LogoutMWRequest.builder()
                .ownerAccount(LogoutMWRequest.OwnerAccount.builder()
                        .personId(personId)
                        .personRoleId(personRoleId)
                        .build())
                .build();
    }

    @Override
    public ChangePasswordMWRequest mapperRequest(ChangePasswordRequest request, String personId, String personRoleId) {
        return ChangePasswordMWRequest.builder()
                .previousPassword(Util.encodeSha512(request.getOldPassword()))
                .newPassword(Util.encodeSha512(request.getNewPassword()))
                .ownerAccount(MWOwnerAccountRequest.builder()
                        .personId(personId)
                        .personRoleId(personRoleId)
                        .build()
                )
                .build();
    }

    @Override
    public BiometricsResponse convertResponse(BiometricStatusMWResponse mwResponse) {
        return BiometricsResponse.builder()
                .status(Objects.equals(mwResponse.getData().getStatusBiometric(), "S"))
                .authenticationType(mwResponse.getData().getAuthenticationType())
                .build();
    }

    public LoginValidationServiceResponse convertResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse) {
        LoginValidationServiceResponse response = new LoginValidationServiceResponse();
        response.setPersonId(data.getPersonId());
        response.setName(mwResponse.getData().getHolderName());
        response.setFullName(mwResponse.getData().getRoleList().get(0).getName());
        response.setUserDeviceId(String.valueOf(mwResponse.getData().getUserEnrollmentId()));
        response.setRolePersonId(String.valueOf(mwResponse.getData().getRoleList().get(0).getRolePersonId()));
        response.setStatusCode("SUCCESS");
        response.setLastConnectionDate(mwResponse.getData().getLastConnectionDate());
        response.setKeyChange(Objects.equals(mwResponse.getData().getKeyChange(), "S"));
        response.setKeyChangeMessage(mwResponse.getData().getKeyChangeMessage());
        return response;
    }

    @Override
    public UpdateBiometricsMWRequest mapperUpdateBiometricRequest(UpdateBiometricsRequest request) {
        return UpdateBiometricsMWRequest.builder()
                .statusBiometric(Boolean.TRUE.equals(request.getStatus()) ? "S" : "N")
                .tokenBiometric(request.getTokenBiometric())
                .typeAuthentication(request.getTypeAuthentication())
                .build();
    }

    @Override
    public DeviceEnrollmentResponse convertResponse(DeviceEnrollmentMWResponse mwResponse) {
        return DeviceEnrollmentResponse.builder()
                .personId(mwResponse.getPersonId())
                .statusCode(mwResponse.getStatusCode().equals("ENROLLED") ? 1 : 2)
                .build();
    }
}
