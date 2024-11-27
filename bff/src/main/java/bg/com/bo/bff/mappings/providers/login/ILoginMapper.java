package bg.com.bo.bff.mappings.providers.login;


import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.login.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.application.dtos.response.user.BiometricsResponse;
import bg.com.bo.bff.providers.dtos.request.login.mw.ChangePasswordMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.UpdateBiometricsMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorData;

public interface ILoginMapper {
    LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data);

    LogoutMWRequest mapperRequest(String personId, String personRoleId);

    ChangePasswordMWRequest mapperRequest(ChangePasswordRequest request,String personId, String personRoleId);

    BiometricsResponse convertResponse(BiometricStatusMWResponse mwResponse);

    LoginValidationServiceResponse convertResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse);

    UpdateBiometricsMWRequest mapperUpdateBiometricRequest(UpdateBiometricsRequest request);

    DeviceEnrollmentResponse convertResponse(DeviceEnrollmentMWResponse mwResponse);
}
