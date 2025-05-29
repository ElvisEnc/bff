package bg.com.bo.bff.providers.models.enums.middleware.onboarding.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OnboardingManagerMiddlewareService {

    GET_PERSON_DEVICES("/bs/v1/devices/person/%s"),
    DISABLE_DEVICE("/bs/v1/devices/desactivation");

    private final String serviceURL;
}
