package bg.com.bo.bff.mappings.providers.login;


import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.user.UpdateBiometricsRequest;
import bg.com.bo.bff.application.dtos.response.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.UpdateBiometricsMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorData;

public interface ILoginMapper {
    LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data);

    LoginValidationServiceResponse converResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse);

    UpdateBiometricsMWRequest mapperUpdateBiometricRequest(UpdateBiometricsRequest request);
}
