package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;

import java.io.IOException;

public interface ILoginMiddlewareProvider {
    ClientToken tokenLogin()throws IOException;
    LoginMWFactorResponse validateFactorUser(LoginRequest loginRequest, String ip, String token) throws IOException;
    LoginValidationServiceResponse validateCredentials(LoginRequest loginRequest, String ip,String token,  LoginMWFactorDataResponse data) throws IOException;
    GenericResponse changePassword(String personId, String ip, String deviceId, String deviceUniqueId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException;
}
