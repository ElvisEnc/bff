package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.requests.login.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;

import java.io.IOException;

public interface ILoginMiddlewareProvider {

    LoginMWFactorResponse validateFactorUser(LoginRequest loginRequest, String ip) throws IOException;

    LoginValidationServiceResponse validateCredentials(LoginRequest loginRequest, String ip, LoginMWFactorDataResponse data) throws IOException;

    GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, LogoutMWRequest logoutMWRequest) throws IOException;

    GenericResponse changePassword(String personId, String ip, String deviceId, String userDeviceId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException;
}
