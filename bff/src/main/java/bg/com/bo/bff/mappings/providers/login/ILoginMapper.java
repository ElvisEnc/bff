package bg.com.bo.bff.mappings.providers.login;


import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.UpdateBiometricsRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.request.login.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.UpdateBiometricsMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.LoginFactorData;

public interface ILoginMapper {
    LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data);

    LoginValidationServiceResponse converResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse);

    UpdateBiometricsMWRequest mapperUpdateBiometricRequest(UpdateBiometricsRequest request);
}
