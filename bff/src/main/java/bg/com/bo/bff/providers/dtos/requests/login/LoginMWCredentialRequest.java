package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginMWCredentialRequest {
    private String personId;
    private String password;
    private LoginMWCredendialDeviceRequest deviceData;
    private String idGeneratorUuid;
    private String loginType;
    private String tokenFinger;
    private String appVersion;
}
