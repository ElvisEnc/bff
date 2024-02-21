package bg.com.bo.bff.services.v1;

import org.springframework.stereotype.Service;

import java.io.IOException;

import bg.com.bo.bff.controllers.request.LoginBiometricRequest;
import bg.com.bo.bff.provider.interfaces.ILoginBiometricProvider;
import bg.com.bo.bff.provider.request.LoginBiometricRequestDTO;
import bg.com.bo.bff.services.interfaces.ILoginBiometricService;

@Service
public class LoginBiometricService implements ILoginBiometricService {

    private final ILoginBiometricProvider provider;

    public LoginBiometricService(ILoginBiometricProvider loginBiometricProvider) {
        this.provider = loginBiometricProvider;
    }

    @Override
    public Object loginBiometric(LoginBiometricRequest request) throws IOException {
        return provider.loginBiometric(LoginBiometricRequestDTO.toParse(request));
    }
}
