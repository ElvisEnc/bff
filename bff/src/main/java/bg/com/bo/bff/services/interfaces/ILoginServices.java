package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.controllers.request.LoginRequest;
import bg.com.bo.bff.model.dtos.login.LoginResult;
import bg.com.bo.bff.controllers.request.RefreshSessionRequest;
import bg.com.bo.bff.model.dtos.login.RefreshSessionResult;

public interface ILoginServices {
    LoginResult login(LoginRequest loginRequest);

    RefreshSessionResult refreshSession(String personId, RefreshSessionRequest refreshSessionRequest);
}
