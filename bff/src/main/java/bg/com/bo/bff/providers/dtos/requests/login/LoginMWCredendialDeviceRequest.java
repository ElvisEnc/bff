package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Builder;

@lombok.Data
@Builder
public class LoginMWCredendialDeviceRequest {
    private String deviceIp;
    private String boot;
    private String uniqueId;
    private String soCodeName;
    private String systemName;
    private String systemVersion;
    private String systemBuildId;
    private String userAgent;
    private String firstInstallTime;
    private String debug;
    private String emulator;
    private String appVersion;
}
