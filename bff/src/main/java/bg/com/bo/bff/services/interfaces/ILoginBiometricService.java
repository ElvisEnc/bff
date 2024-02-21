package bg.com.bo.bff.services.interfaces;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.LoginBiometricRequest;

public interface ILoginBiometricService {
    Object loginBiometric(LoginBiometricRequest request) throws IOException;

}
