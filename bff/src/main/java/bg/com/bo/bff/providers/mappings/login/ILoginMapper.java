package bg.com.bo.bff.providers.mappings.login;


import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.requests.login.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginFactorData;

public interface ILoginMapper {
    LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data);

    LoginValidationServiceResponse converResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse);
}
