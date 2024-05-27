package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
import bg.com.bo.bff.models.dtos.login.LoginResult;
import bg.com.bo.bff.application.dtos.request.RefreshSessionRequest;

import java.io.IOException;
import java.util.Map;

public interface ILoginServices {
    LoginResult login(LoginRequest loginRequest, Map<String, String> parameters) throws IOException;

    TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest);

    GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, String personId, String userDeviceId, String personRoleId, String authorization, LogoutRequest request) throws IOException;
}
