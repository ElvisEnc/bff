package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
import bg.com.bo.bff.models.dtos.login.LoginResult;
import bg.com.bo.bff.application.dtos.request.RefreshSessionRequest;
import bg.com.bo.bff.models.dtos.login.RefreshSessionResult;

import java.io.IOException;

public interface ILoginServices {
    LoginResult login(LoginRequest loginRequest, String ip) throws IOException;

    TokenDataResponse  refreshSession(String personId, RefreshSessionRequest refreshSessionRequest);
}
