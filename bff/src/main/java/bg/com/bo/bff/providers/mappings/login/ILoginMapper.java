package bg.com.bo.bff.providers.mappings.login;


import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWCredentialRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;

public interface ILoginMapper {
    LoginMWCredentialRequest mapperRequest(LoginRequest loginRequest, String ip, LoginMWFactorDataResponse data);
}
