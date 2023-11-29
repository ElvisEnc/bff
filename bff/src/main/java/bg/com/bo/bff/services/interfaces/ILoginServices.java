package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.model.LoginRequest;
import bg.com.bo.bff.model.LoginResponse;

import java.io.IOException;

public interface ILoginServices {
    LoginResponse loginRequest(LoginRequest loginRequest) throws IOException;
}
