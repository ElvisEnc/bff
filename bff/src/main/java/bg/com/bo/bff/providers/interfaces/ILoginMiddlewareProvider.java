package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.response.user.UpdateBiometricsResponse;
import bg.com.bo.bff.application.dtos.response.user.ContactResponse;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.request.login.mw.*;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.dtos.response.login.mw.*;

import java.io.IOException;

public interface ILoginMiddlewareProvider {

    LoginFactorMWResponse validateFactorUser(LoginFactorMWRequest loginRequest) throws IOException;

    LoginCredentialMWResponse validateCredentials(LoginCredentialMWRequest loginRequest) throws IOException;

    LogoutMWResponse logout(LogoutMWRequest logoutMWRequest) throws IOException;

    ChangePasswordMWResponse changePassword(ChangePasswordMWRequest mwRequest) throws IOException;

    ContactResponse getContactInfo();

    DeviceEnrollmentMWResponse makeValidateDevice() throws IOException;

    BiometricStatusMWResponse getBiometricsMW(Integer personId) throws IOException;

    UpdateBiometricsResponse updateBiometricsMW(Integer personId, UpdateBiometricsMWRequest request) throws IOException;

    UserEncryptionKeys getEncryptionKeys(EncryptInfo encodeInfo) throws IOException;
}
