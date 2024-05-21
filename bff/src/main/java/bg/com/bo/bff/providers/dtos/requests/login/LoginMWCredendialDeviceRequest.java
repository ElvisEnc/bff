package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginMWCredendialDeviceRequest {
    private String deviceIp;
    private String deviceId;
    private String deviceName;
    private String geoPositionX;
    private String geoPositionY;
}
