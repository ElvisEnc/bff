package bg.com.bo.bff.provider.interfaces;

import bg.com.bo.bff.controllers.request.LoginRequest;
import bg.com.bo.bff.model.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.model.dtos.login.LoginValidationServiceResponse;

public interface ILoginMiddlewareProvider {
    ClientMWToken generateAccessToken();

    LoginValidationServiceResponse validateCredentials(String token, LoginRequest loginRequest);
}
