package bg.com.bo.bff.application.dtos.response.onboarding.manager;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnboardingManagerResponse {

    private int personId;
    private String deviceId;
    private String phone;
    private String model;
    private String registrationDate;
    private String migrationDate;
    private String lastConnectionDate;
    private int failedAttemptsCount;
    private String statusCode;
    private String session;
    private String deviceIp;
    private String osCode;
    private String osName;
    private String osVersion;
    private String compiledOsCode;
    private String biometricEnablement;
    private String biometricToken;
    private String agentUser;
    private String installationDate;
    private String registeredUser;
    private int authenticationTypeCode;
    private String roleCode;

}
