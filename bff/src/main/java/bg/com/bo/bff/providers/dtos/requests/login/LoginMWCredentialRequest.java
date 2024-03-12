package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Builder;

@lombok.Data
@Builder
public class LoginMWCredentialRequest {
    private String personId;
    private String password;
    private String geoReference;
    private LoginMWCredendialDeviceRequest deviceIdentification;
    private String idGeneratorUuid;
    private String loginType;
    private String tokenFinger;
}
