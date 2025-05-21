package bg.com.bo.bff.application.dtos.response.onboarding.manager;

import java.util.List;

public class OnboardingManagerResponseFixture {

    public static List<OnboardingManagerResponse> withDefaultGellAllDevice() {
        return List.of(
                OnboardingManagerResponse.builder()
                        .personId(151515)
                        .deviceId("device0001")
                        .phone("7497777777")
                        .model("A7")
                        .registrationDate("2025/04/08")
                        .migrationDate("2025/04/08")
                        .lastConnectionDate("2025/04/08")
                        .failedAttemptsCount(0)
                        .statusCode("A")
                        .session("abcde321")
                        .deviceIp("localhost")
                        .osCode("A8")
                        .osName("Android")
                        .osVersion("A8")
                        .compiledOsCode("8")
                        .biometricEnablement("OK")
                        .biometricToken("OK")
                        .agentUser("5464546")
                        .installationDate("2025/04/08")
                        .registeredUser("2025/04/08")
                        .authenticationTypeCode(2)
                        .roleCode("1").build());
    }
    
}