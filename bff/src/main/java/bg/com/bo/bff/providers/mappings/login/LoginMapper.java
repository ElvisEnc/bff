package bg.com.bo.bff.providers.mappings.login;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.providers.dtos.requests.login.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginCredentialMWResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginFactorData;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LoginMapper implements ILoginMapper {
    public LoginCredentialMWRequest mapperRequest(LoginRequest loginRequest, LoginFactorData data){
        return LoginCredentialMWRequest.builder()
                .personId(data.getPersonId())
                .password(loginRequest.getPassword())
                .idGeneratorUuid(data.getIdGeneratorUuid())
                .loginType(loginRequest.getType())
                .tokenFinger(loginRequest.getTokenBiometric())
                .build();
    }

    public LoginValidationServiceResponse converResponse(LoginFactorData data, LoginCredentialMWResponse mwResponse){
        LoginValidationServiceResponse response= new LoginValidationServiceResponse();
        response.setPersonId(data.getPersonId());
        response.setUserDeviceId(String.valueOf(mwResponse.getData().getUserDeviceId()));
        response.setRolePersonId(String.valueOf(mwResponse.getData().getRoleList().get(0).getRolePersonId()));
        response.setStatusCode("SUCCESS");
        response.setLastConnectionDate(mwResponse.getData().getLastConnectionDate());
        response.setKeyChange(Objects.equals(mwResponse.getData().getKeyChange(), "S"));
        response.setKeyChangeMessage(mwResponse.getData().getKeyChangeMessage());
        return response;
    }
}
