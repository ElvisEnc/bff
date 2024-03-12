package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Builder;

@lombok.Data
@Builder
public class LoginMWFactorDeviceRequest {
    private String deviceIp;
    private String uniqueId;
}
