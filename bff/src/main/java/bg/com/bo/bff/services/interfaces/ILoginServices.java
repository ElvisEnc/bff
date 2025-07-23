package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.response.login.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.login.LoginResponse;
import bg.com.bo.bff.application.dtos.response.login.TokenDataResponse;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;

import java.io.IOException;

public interface ILoginServices {
    LoginResponse login(LoginRequest loginRequest) throws IOException;

    TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest, String accessJwt);

    GenericResponse logout( String personId, String authorization, String personRoleId, LogoutRequest request) throws IOException;

    DeviceEnrollmentResponse validation() throws IOException;
}
