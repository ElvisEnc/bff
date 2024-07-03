package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.user.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.request.login.mw.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.mw.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorData;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ILoginMiddlewareProvider {

    LoginFactorMWResponse validateFactorUser(LoginRequest loginRequest, Map<String, String> parameters) throws IOException;

    LoginValidationServiceResponse validateCredentials(LoginRequest loginRequest, LoginFactorData data, Map<String, String> parameters) throws IOException;

    GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, LogoutMWRequest logoutMWRequest) throws IOException;

    GenericResponse changePassword(String personId, String personRoleId, ChangePasswordRequest changePasswordRequest, Map<String, String> parameters) throws IOException;

    ContactResponse getContactInfo();

    DeviceEnrollmentMWResponse makeValidateDevice(Map<String, String> parameter) throws IOException;

    BiometricStatusMWResponse getBiometricsMW(Integer personId, Map<String, String> parameter) throws IOException;

    UpdateBiometricsResponse updateBiometricsMW(Integer personId, UpdateBiometricsRequest request, Map<String, String> parameter) throws IOException;
}
