package bg.com.bo.bff.mappings.providers.onboarding.manager;

import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OnboardingManagerMapper implements IOnboardingManagerMapper {


    @Override
    public List<OnboardingManagerResponse> convertResponse(ListDevicesMWResponse mwResponse) {
        if (mwResponse == null || mwResponse.getData() == null)
            return Collections.emptyList();
        return mwResponse.getData().stream()
                .map(mw -> OnboardingManagerResponse.builder()
                        .personId(mw.getPersonNumber())
                        .deviceId(mw.getDeviceIdentifier())
                        .phone(mw.getPhoneNumber())
                        .model(mw.getModel())
                        .registrationDate(mw.getRegistrationDate())
                        .migrationDate(mw.getMigrationDate())
                        .lastConnectionDate(mw.getLastConnectionDate())
                        .failedAttemptsCount(mw.getFailedAttemptsCount())
                        .statusCode(mw.getStatusCode())
                        .session(mw.getSession())
                        .deviceIp(mw.getDeviceIp())
                        .osCode(mw.getOsCode())
                        .osName(mw.getOsName())
                        .osVersion(mw.getOsVersion())
                        .compiledOsCode(mw.getCompiledOsCode())
                        .biometricEnablement(mw.getBiometricEnablement())
                        .biometricToken(mw.getBiometricToken())
                        .agentUser(mw.getAgentUser())
                        .registeredUser(mw.getRegisteredUser())
                        .installationDate(mw.getInstallationDate())
                        .authenticationTypeCode(mw.getAuthenticationTypeCode())
                        .roleCode(mw.getRoleCode())
                        .build()
                ).toList();
    }


}
