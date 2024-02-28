package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;

public interface ILoginMiddlewareProvider {
    ClientMWToken generateAccessToken();

    LoginValidationServiceResponse validateCredentials(String token, LoginRequest loginRequest);
}
