package bg.com.bo.bff.provider.interfaces;

import java.io.IOException;

import bg.com.bo.bff.provider.request.LoginBiometricRequestDTO;

public interface ILoginBiometricProvider {

    Object loginBiometric(LoginBiometricRequestDTO requestDTO) throws IOException;

}
