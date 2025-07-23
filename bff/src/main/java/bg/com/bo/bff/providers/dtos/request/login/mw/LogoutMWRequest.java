package bg.com.bo.bff.providers.dtos.request.login.mw;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutMWRequest {
    private OwnerAccount ownerAccount;
    private LogoutDataMW deviceData;

    @Data
    @Builder
    public static class OwnerAccount {
        String personId;
        String userDeviceId;
        String personRoleId;
    }

    @Data
    @Builder
    public static class LogoutDataMW {
        String deviceId;
        String deviceIp;
        String deviceName;
        String geoPositionX;
        String geoPositionY;
        String appVersion;
    }
}
