package bg.com.bo.bff;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Custom Headers")
public class HeaderDeviceMW {
    @Schema(description = "device-id")
    private String deviceId;
    @Schema(description = "device-ip")
    private String deviceIp;
    @Schema(description = "device-name")
    private String deviceName;
    @Schema(description = "geo-position-x")
    private String geoPositionX;
    @Schema(description = "geo-position-y")
    private String geoPositionY;
    @Schema(description = "app-version")
    private String appVersion;
}
