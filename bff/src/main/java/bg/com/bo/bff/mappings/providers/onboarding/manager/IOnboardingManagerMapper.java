package bg.com.bo.bff.mappings.providers.onboarding.manager;

import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;
import bg.com.bo.bff.providers.dtos.request.onboarding.manager.mw.DisableDeviceMWRequest;
import bg.com.bo.bff.providers.dtos.response.onboarding.manager.mw.ListDevicesMWResponse;

import java.util.List;


public interface IOnboardingManagerMapper {
    List<OnboardingManagerResponse> convertResponse(ListDevicesMWResponse mwResponse);

    DisableDeviceMWRequest mapToDisableDeviceRequest(int personId, String deviseId);
}
