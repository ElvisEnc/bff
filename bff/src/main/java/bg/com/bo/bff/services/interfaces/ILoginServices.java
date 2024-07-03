package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.response.login.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.login.TokenDataResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginResult;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;

import java.io.IOException;
import java.util.Map;

public interface ILoginServices {
    LoginResult login(LoginRequest loginRequest, Map<String, String> parameters) throws IOException;

    TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest);

    GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, String personId, String userDeviceId, String personRoleId, String authorization, LogoutRequest request) throws IOException;
    DeviceEnrollmentResponse validation(Map<String, String> parameter) throws IOException;
}
