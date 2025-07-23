package bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDevicesMWResponse {
    private List<DeviceMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceMW {
        private int identifier;
        private int personNumber;
        private String phoneNumber;
        private String system;
        private String model;
        private String registrationDate;
        private String migrationDate;
        private String lastConnectionDate;
        private int failedAttemptsCount;
        private String statusCode;
        private int applicationCode;
        private String session;
        private String deviceIdentifierK;
        private String deviceIdentifier;
        private String deviceIp;
        private String boot;
        private String osCode;
        private String osName;
        private String osVersion;
        private String compiledOsCode;
        private String biometricEnablement;
        private String biometricToken;
        private String agentUser;
        private String installationDate;
        private int debugging;
        private int emulator;
        private String registeredUser;
        private String userRegistrationDate;
        private int registrationBranch;
        private String modifiedUser;
        private String modificationDate;
        private String modificationBranch;
        private int authenticationTypeCode;
        private String clientId;
        private String roleCode;
        private String clientProtocol;
        private String accessType;
        private String clientSecret;
    }
}
