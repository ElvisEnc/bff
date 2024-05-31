package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.request.login.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.BiometricStatusMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.LoginFactorData;
import bg.com.bo.bff.providers.dtos.response.login.LoginFactorMWResponse;

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
