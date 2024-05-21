package bg.com.bo.bff.providers.mappings.login;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWCredendialDeviceRequest;
import bg.com.bo.bff.providers.dtos.requests.login.LoginMWCredentialRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper implements ILoginMapper {
    public LoginMWCredentialRequest mapperRequest(LoginRequest loginRequest, String ip, LoginMWFactorDataResponse data){
        LoginMWCredendialDeviceRequest loginMWCredendialDeviceRequest = LoginMWCredendialDeviceRequest.builder()
                .deviceIp(ip)
                .deviceId(loginRequest.getDeviceIdentification().getUniqueId())
                .deviceName(loginRequest.getDeviceIdentification().getSystemName())
                .geoPositionX(loginRequest.getDeviceIdentification().getGeoPositionX())
                .geoPositionY(loginRequest.getDeviceIdentification().getGeoPositionY())
                .build();

        return LoginMWCredentialRequest.builder()
                .personId(data.getPersonId())
                .password(loginRequest.getPassword())
                .deviceData(loginMWCredendialDeviceRequest)
                .idGeneratorUuid(data.getIdGeneratorUuid())
                .loginType(loginRequest.getType())
                .tokenFinger(data.getSecondFactor())
                .appVersion(loginRequest.getAppVersion())
                .build();
    }
}
