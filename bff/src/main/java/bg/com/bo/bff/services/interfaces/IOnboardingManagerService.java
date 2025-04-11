package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.dtos.response.onboarding.manager.OnboardingManagerResponse;

import java.io.IOException;
import java.util.List;


public interface IOnboardingManagerService {

    List<OnboardingManagerResponse> getAllDevices(int personId) throws IOException;

    GenericResponse disableDevice(int personId, String deviceId) throws IOException;

}






